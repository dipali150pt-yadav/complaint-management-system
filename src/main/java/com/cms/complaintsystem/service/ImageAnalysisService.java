package com.cms.complaintsystem.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.Base64;

@Service
public class ImageAnalysisService {

    @Value("${ai.provider:openai}")
    private String aiProvider;

    @Value("${openai.api.key:}")
    private String openaiApiKey;

    @Value("${google.cloud.vision.api.key:}")
    private String googleApiKey;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public ImageAnalysisService() {
        this.webClient = WebClient.builder().build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Main method to analyze image and return civic issue details
     */
    public ImageAnalysisResult analyzeImage(File imageFile) throws IOException {
        
        if (aiProvider.equalsIgnoreCase("openai") && !openaiApiKey.isEmpty()) {
            return analyzeWithOpenAI(imageFile);
        } else if (aiProvider.equalsIgnoreCase("google") && !googleApiKey.isEmpty()) {
            return analyzeWithGoogleVision(imageFile);
        } else {
            // Fallback to local rule-based analysis
            return analyzeWithLocalRules(imageFile);
        }
    }

    /**
     * Analyze image using OpenAI GPT-4 Vision API
     */
    private ImageAnalysisResult analyzeWithOpenAI(File imageFile) throws IOException {
        
        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        
        String prompt = """
            You are an AI assistant for a Smart Civic Complaint Management System.
            Analyze this image and identify civic issues like:
            - Potholes or road damage
            - Garbage accumulation or sanitation issues
            - Broken streetlights or electrical issues
            - Water leakage or drainage problems
            - Damaged infrastructure (walls, sidewalks, etc.)
            - Stray animals creating problems
            - Illegal dumping
            - Any other municipal issues
            
            Provide your response in this exact JSON format:
            {
                "issueType": "primary issue category",
                "detectedIssues": ["list of specific issues found"],
                "description": "detailed description of the problem",
                "severity": "LOW/MEDIUM/HIGH/CRITICAL",
                "confidence": 0.95,
                "suggestedDepartment": "appropriate municipal department",
                "recommendations": "suggested action steps"
            }
            """;

        try {
            String requestBody = String.format("""
                {
                    "model": "gpt-4o-mini",
                    "messages": [
                        {
                            "role": "user",
                            "content": [
                                {
                                    "type": "text",
                                    "text": "%s"
                                },
                                {
                                    "type": "image_url",
                                    "image_url": {
                                        "url": "data:image/jpeg;base64,%s"
                                    }
                                }
                            ]
                        }
                    ],
                    "max_tokens": 500
                }
                """, prompt.replace("\n", "\\n"), base64Image);

            String response = webClient.post()
                    .uri("https://api.openai.com/v1/chat/completions")
                    .header("Authorization", "Bearer " + openaiApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return parseOpenAIResponse(response);
            
        } catch (Exception e) {
            System.err.println("OpenAI API Error: " + e.getMessage());
            // Fallback to local analysis
            return analyzeWithLocalRules(imageFile);
        }
    }

    /**
     * Analyze image using Google Cloud Vision API
     */
    private ImageAnalysisResult analyzeWithGoogleVision(File imageFile) throws IOException {
        
        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        try {
            String requestBody = String.format("""
                {
                    "requests": [{
                        "image": {
                            "content": "%s"
                        },
                        "features": [
                            {"type": "LABEL_DETECTION", "maxResults": 10},
                            {"type": "OBJECT_LOCALIZATION", "maxResults": 10},
                            {"type": "IMAGE_PROPERTIES"}
                        ]
                    }]
                }
                """, base64Image);

            String response = webClient.post()
                    .uri("https://vision.googleapis.com/v1/images:annotate?key=" + googleApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return parseGoogleVisionResponse(response);
            
        } catch (Exception e) {
            System.err.println("Google Vision API Error: " + e.getMessage());
            return analyzeWithLocalRules(imageFile);
        }
    }

    /**
     * Fallback: Local rule-based analysis using filename and basic heuristics
     */
    private ImageAnalysisResult analyzeWithLocalRules(File imageFile) {
        
        ImageAnalysisResult result = new ImageAnalysisResult();
        
        String filename = imageFile.getName().toLowerCase();
        
        // Analyze based on filename patterns (common keywords)
        if (filename.contains("pothole") || filename.contains("road") || filename.contains("street")) {
            result.setIssueType("Road Damage");
            result.setDetectedIssues(Arrays.asList("Pothole", "Road surface damage"));
            result.setDescription("Road damage detected. Requires immediate repair to prevent accidents.");
            result.setSeverity("HIGH");
            result.setSuggestedDepartment("Roads & Infrastructure");
            result.setConfidence(0.70);
            
        } else if (filename.contains("garbage") || filename.contains("trash") || filename.contains("waste")) {
            result.setIssueType("Sanitation Issue");
            result.setDetectedIssues(Arrays.asList("Garbage accumulation", "Waste management"));
            result.setDescription("Garbage accumulation reported. Requires immediate collection and disposal.");
            result.setSeverity("MEDIUM");
            result.setSuggestedDepartment("Sanitation & Waste Management");
            result.setConfidence(0.75);
            
        } else if (filename.contains("light") || filename.contains("street") || filename.contains("lamp")) {
            result.setIssueType("Street Lighting");
            result.setDetectedIssues(Arrays.asList("Broken streetlight", "Non-functional light"));
            result.setDescription("Street lighting issue reported. Requires repair or replacement.");
            result.setSeverity("MEDIUM");
            result.setSuggestedDepartment("Street Lighting & Electrical");
            result.setConfidence(0.72);
            
        } else if (filename.contains("water") || filename.contains("leak") || filename.contains("drain")) {
            result.setIssueType("Water Supply Issue");
            result.setDetectedIssues(Arrays.asList("Water leakage", "Drainage problem"));
            result.setDescription("Water-related issue detected. Requires investigation and repair.");
            result.setSeverity("HIGH");
            result.setSuggestedDepartment("Water Supply & Drainage");
            result.setConfidence(0.68);
            
        } else {
            result.setIssueType("General Civic Issue");
            result.setDetectedIssues(Arrays.asList("Municipal issue reported"));
            result.setDescription("General civic complaint requiring municipal attention.");
            result.setSeverity("MEDIUM");
            result.setSuggestedDepartment("General Administration");
            result.setConfidence(0.60);
        }
        
        result.setRecommendations("Please review the uploaded image and take appropriate action.");
        
        return result;
    }

    /**
     * Parse OpenAI API response
     */
    private ImageAnalysisResult parseOpenAIResponse(String response) throws IOException {
        
        JsonNode root = objectMapper.readTree(response);
        String content = root.path("choices").get(0).path("message").path("content").asText();
        
        // Extract JSON from markdown code blocks if present
        if (content.contains("```json")) {
            content = content.substring(content.indexOf("```json") + 7);
            content = content.substring(0, content.indexOf("```"));
        } else if (content.contains("```")) {
            content = content.substring(content.indexOf("```") + 3);
            content = content.substring(0, content.indexOf("```"));
        }
        
        JsonNode analysisNode = objectMapper.readTree(content.trim());
        
        ImageAnalysisResult result = new ImageAnalysisResult();
        result.setIssueType(analysisNode.path("issueType").asText("General Issue"));
        result.setDescription(analysisNode.path("description").asText());
        result.setSeverity(analysisNode.path("severity").asText("MEDIUM"));
        result.setConfidence(analysisNode.path("confidence").asDouble(0.85));
        result.setSuggestedDepartment(analysisNode.path("suggestedDepartment").asText("General"));
        result.setRecommendations(analysisNode.path("recommendations").asText());
        
        List<String> issues = new ArrayList<>();
        analysisNode.path("detectedIssues").forEach(node -> issues.add(node.asText()));
        result.setDetectedIssues(issues);
        
        return result;
    }

    /**
     * Parse Google Vision API response
     */
    private ImageAnalysisResult parseGoogleVisionResponse(String response) throws IOException {
        
        JsonNode root = objectMapper.readTree(response);
        JsonNode annotations = root.path("responses").get(0);
        
        List<String> labels = new ArrayList<>();
        annotations.path("labelAnnotations").forEach(label -> 
            labels.add(label.path("description").asText())
        );
        
        // Map Google Vision labels to civic issues
        ImageAnalysisResult result = mapLabelsToIssues(labels);
        result.setConfidence(0.80);
        
        return result;
    }

    /**
     * Map detected labels to civic issue categories
     */
    private ImageAnalysisResult mapLabelsToIssues(List<String> labels) {
        
        ImageAnalysisResult result = new ImageAnalysisResult();
        String labelsStr = String.join(" ", labels).toLowerCase();
        
        if (labelsStr.contains("road") || labelsStr.contains("asphalt") || labelsStr.contains("crack")) {
            result.setIssueType("Road Damage");
            result.setSuggestedDepartment("Roads & Infrastructure");
            result.setDescription("Road infrastructure issue detected from image analysis.");
            result.setSeverity("HIGH");
            
        } else if (labelsStr.contains("garbage") || labelsStr.contains("waste") || labelsStr.contains("trash")) {
            result.setIssueType("Sanitation Issue");
            result.setSuggestedDepartment("Sanitation & Waste Management");
            result.setDescription("Sanitation and waste management issue detected.");
            result.setSeverity("MEDIUM");
            
        } else if (labelsStr.contains("light") || labelsStr.contains("lamp") || labelsStr.contains("street")) {
            result.setIssueType("Street Lighting");
            result.setSuggestedDepartment("Street Lighting & Electrical");
            result.setDescription("Street lighting issue detected.");
            result.setSeverity("MEDIUM");
            
        } else if (labelsStr.contains("water") || labelsStr.contains("leak") || labelsStr.contains("pipe")) {
            result.setIssueType("Water Supply Issue");
            result.setSuggestedDepartment("Water Supply & Drainage");
            result.setDescription("Water supply or drainage issue detected.");
            result.setSeverity("HIGH");
            
        } else {
            result.setIssueType("General Civic Issue");
            result.setSuggestedDepartment("General Administration");
            result.setDescription("Civic issue requiring municipal attention.");
            result.setSeverity("MEDIUM");
        }
        
        result.setDetectedIssues(labels);
        result.setRecommendations("Issue detected through image analysis. Please verify and take action.");
        
        return result;
    }

    /**
     * Inner class to hold analysis results
     */
    public static class ImageAnalysisResult {
        private String issueType;
        private List<String> detectedIssues;
        private String description;
        private String severity;
        private Double confidence;
        private String suggestedDepartment;
        private String recommendations;

        // Getters and Setters
        public String getIssueType() {
            return issueType;
        }

        public void setIssueType(String issueType) {
            this.issueType = issueType;
        }

        public List<String> getDetectedIssues() {
            return detectedIssues;
        }

        public void setDetectedIssues(List<String> detectedIssues) {
            this.detectedIssues = detectedIssues;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSeverity() {
            return severity;
        }

        public void setSeverity(String severity) {
            this.severity = severity;
        }

        public Double getConfidence() {
            return confidence;
        }

        public void setConfidence(Double confidence) {
            this.confidence = confidence;
        }

        public String getSuggestedDepartment() {
            return suggestedDepartment;
        }

        public void setSuggestedDepartment(String suggestedDepartment) {
            this.suggestedDepartment = suggestedDepartment;
        }

        public String getRecommendations() {
            return recommendations;
        }

        public void setRecommendations(String recommendations) {
            this.recommendations = recommendations;
        }
    }
}
