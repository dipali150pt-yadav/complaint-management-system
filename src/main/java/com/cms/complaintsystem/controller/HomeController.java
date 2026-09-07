package com.cms.complaintsystem.controller;

import com.cms.complaintsystem.model.Complaint;
import com.cms.complaintsystem.repository.ComplaintRepository;
import com.cms.complaintsystem.service.AIService;
import com.cms.complaintsystem.service.ComplaintGenerationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class HomeController {

    @Autowired
    private ComplaintRepository repository;

    @Autowired
    private AIService aiService;

    @Autowired
    private ComplaintGenerationService complaintGenerationService;

    // =============================
    // ROOT PAGE - REDIRECT TO HOME
    // =============================
    @GetMapping("/")
    public String start() {
        return "redirect:/home";
    }

    // =============================
    // LOGIN PAGE
    // =============================
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // =============================
    // LOGIN PROCESS
    // =============================
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {

        // ADMIN LOGIN
        if(username.equals("admin") && password.equals("admin123")) {
            return "redirect:/admin";
        }

        // USER LOGIN
        if(username.equals("user") && password.equals("user123")) {
            return "redirect:/home";
        }

        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    // =============================
    // USER HOME PAGE
    // =============================
    @GetMapping("/home")
    public String home() {
        return "index";
    }

    // =============================
    // TRACK PAGE
    // =============================
    @GetMapping("/track")
    public String trackPage() {
        return "track";
    }

    // =============================
    // TRACK COMPLAINT
    // =============================
    @PostMapping("/track")
    public String trackComplaint(@RequestParam("id") Long id, Model model) {

        Complaint complaint = repository.findById(id).orElse(null);

        model.addAttribute("complaint", complaint);

        return "track";
    }

    // =============================
    // SUBMIT CIVIC COMPLAINT WITH AI
    // =============================
    @PostMapping("/submit")
    public String submitComplaint(@RequestParam(value = "complaint", required = false) String text,
                                  @RequestParam(value = "image", required = true) MultipartFile file,
                                  @RequestParam(value = "latitude", required = false) Double latitude,
                                  @RequestParam(value = "longitude", required = false) Double longitude,
                                  @RequestParam(value = "address", required = false) String address,
                                  @RequestParam(value = "landmark", required = false) String landmark,
                                  @RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "contact", required = false) String contact,
                                  Model model) throws IOException {

        // Validate that image is provided
        if (file.isEmpty()) {
            model.addAttribute("error", "Please upload an image of the civic issue");
            return "index";
        }

        // Step 1: Save uploaded image to uploads directory
        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String filePath = uploadDir + fileName;
        File imageFile = new File(filePath);
        file.transferTo(imageFile);

        try {
            // Step 2: Generate complaint using AI from image
            Complaint complaint = complaintGenerationService.generateComplaintFromImage(
                imageFile,
                text,           // User-provided description (optional)
                latitude,       // GPS latitude (optional)
                longitude,      // GPS longitude (optional)
                address,        // Address (optional)
                landmark,       // Nearby landmark (optional)
                name != null && !name.isEmpty() ? name : "Anonymous Citizen",
                contact         // Contact number (optional)
            );

            // Step 3: Save complaint to database
            Complaint savedComplaint = repository.save(complaint);

            // Step 4: Prepare success page data
            model.addAttribute("complaintId", savedComplaint.getId());
            model.addAttribute("category", savedComplaint.getCategory());
            model.addAttribute("department", savedComplaint.getDepartment());
            model.addAttribute("severity", savedComplaint.getSeverity());
            model.addAttribute("priority", savedComplaint.getPriority());
            model.addAttribute("confidence", 
                savedComplaint.getAiConfidence() != null ? 
                String.format("%.0f%%", savedComplaint.getAiConfidence() * 100) : "N/A");
            model.addAttribute("description", savedComplaint.getAutoGeneratedDescription());
            model.addAttribute("detectedIssues", savedComplaint.getDetectedIssues());
            model.addAttribute("estimatedResolution", 
                complaintGenerationService.estimateResolutionTime(savedComplaint));
            model.addAttribute("message", 
                complaintGenerationService.generateCitizenMessage(savedComplaint));

            return "success";

        } catch (Exception e) {
            // Fallback: If AI processing fails, create basic complaint
            System.err.println("AI Processing Error: " + e.getMessage());
            e.printStackTrace();

            Complaint fallbackComplaint = new Complaint();
            fallbackComplaint.setText(text != null ? text : "Civic issue reported via image");
            fallbackComplaint.setImagePath(filePath);
            fallbackComplaint.setStatus("Pending");
            fallbackComplaint.setSubmittedAt(LocalDateTime.now());
            fallbackComplaint.setLatitude(latitude);
            fallbackComplaint.setLongitude(longitude);
            fallbackComplaint.setAddress(address);
            fallbackComplaint.setLandmark(landmark);
            fallbackComplaint.setSubmittedBy(name != null ? name : "Anonymous Citizen");
            fallbackComplaint.setContactNumber(contact);

            // Use text-based AI classification
            String category = aiService.categorizeComplaint(text != null ? text : "");
            String department = aiService.detectDepartment(text != null ? text : "");
            
            fallbackComplaint.setCategory(category);
            fallbackComplaint.setDepartment(department);
            fallbackComplaint.setSeverity("MEDIUM");
            fallbackComplaint.setPriority(3);

            Complaint savedComplaint = repository.save(fallbackComplaint);

            model.addAttribute("complaintId", savedComplaint.getId());
            model.addAttribute("category", category);
            model.addAttribute("department", department);
            model.addAttribute("severity", "MEDIUM");
            model.addAttribute("priority", 3);
            model.addAttribute("confidence", "Text-based classification");
            model.addAttribute("description", "Complaint registered. AI image analysis unavailable.");
            model.addAttribute("message", "Your complaint has been registered successfully!");

            return "success";
        }
    }

    // =============================
    // SUBMIT COMPLAINT (OLD METHOD - keeping for backward compatibility)
    // =============================
    @PostMapping("/submit-basic")
    public String submitBasicComplaint(@RequestParam("complaint") String text,
                                      @RequestParam("image") MultipartFile file,
                                      Model model) throws IOException {

        Complaint complaint = new Complaint();
        complaint.setText(text);
        complaint.setStatus("Pending");
        complaint.setSubmittedAt(LocalDateTime.now());

        // AI CATEGORY
        String category = aiService.categorizeComplaint(text);
        complaint.setCategory(category);

        // AI DEPARTMENT
        String department = aiService.detectDepartment(text);
        complaint.setDepartment(department);

        // Set default severity and priority
        complaint.setSeverity("MEDIUM");
        complaint.setPriority(3);

        // IMAGE UPLOAD
        if (!file.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filePath = uploadDir + file.getOriginalFilename();
            File destination = new File(filePath);
            file.transferTo(destination);

            complaint.setImagePath(filePath);
        }

        repository.save(complaint);

        model.addAttribute("complaintId", complaint.getId());
        model.addAttribute("category", category);
        model.addAttribute("department", department);

        return "success";
    }
}