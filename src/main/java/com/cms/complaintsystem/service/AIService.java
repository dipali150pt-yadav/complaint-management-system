package com.cms.complaintsystem.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AIService {

    // =============================
    // CIVIC COMPLAINT CATEGORY DETECTION
    // =============================
    public String categorizeComplaint(String text) {

        if (text == null || text.isEmpty()) {
            return "GENERAL";
        }

        text = text.toLowerCase();
        List<String> categories = new ArrayList<>();

        // ROAD & INFRASTRUCTURE
        if (text.contains("pothole") || text.contains("road") || text.contains("street") || 
            text.contains("pavement") || text.contains("footpath") || text.contains("sidewalk") ||
            text.contains("crack") || text.contains("damaged road") || text.contains("highway")) {
            categories.add("ROADS & INFRASTRUCTURE");
        }

        // SANITATION & WASTE MANAGEMENT
        if (text.contains("garbage") || text.contains("trash") || text.contains("waste") ||
            text.contains("dirty") || text.contains("clean") || text.contains("dump") ||
            text.contains("sanitation") || text.contains("litter") || text.contains("dustbin") ||
            text.contains("sweeping") || text.contains("smelly")) {
            categories.add("SANITATION");
        }

        // STREET LIGHTING
        if (text.contains("light") || text.contains("streetlight") || text.contains("lamp") ||
            text.contains("bulb") || text.contains("dark") || text.contains("lighting") ||
            text.contains("street light") || text.contains("pole")) {
            categories.add("STREET LIGHTING");
        }

        // WATER SUPPLY & DRAINAGE
        if (text.contains("water") || text.contains("leak") || text.contains("drain") ||
            text.contains("sewage") || text.contains("pipeline") || text.contains("tap") ||
            text.contains("supply") || text.contains("overflow") || text.contains("clog") ||
            text.contains("waterlog") || text.contains("flood")) {
            categories.add("WATER & DRAINAGE");
        }

        // ELECTRICITY & POWER
        if (text.contains("electric") || text.contains("power") || text.contains("transformer") ||
            text.contains("cable") || text.contains("wire") || text.contains("voltage") ||
            text.contains("meter") || text.contains("outage")) {
            categories.add("ELECTRICITY");
        }

        // PARKS & GARDENS
        if (text.contains("park") || text.contains("garden") || text.contains("tree") ||
            text.contains("plant") || text.contains("green") || text.contains("playground")) {
            categories.add("PARKS & GARDENS");
        }

        // STRAY ANIMALS
        if (text.contains("dog") || text.contains("animal") || text.contains("stray") ||
            text.contains("cattle") || text.contains("cow") || text.contains("menace")) {
            categories.add("STRAY ANIMALS");
        }

        // ILLEGAL CONSTRUCTION / ENCROACHMENT
        if (text.contains("illegal") || text.contains("encroachment") || text.contains("unauthorized") ||
            text.contains("construction") || text.contains("building")) {
            categories.add("ILLEGAL CONSTRUCTION");
        }

        // PUBLIC TOILETS
        if (text.contains("toilet") || text.contains("washroom") || text.contains("restroom") ||
            text.contains("urinal") || text.contains("bathroom")) {
            categories.add("PUBLIC FACILITIES");
        }

        // TRAFFIC & PARKING
        if (text.contains("traffic") || text.contains("parking") || text.contains("signal") ||
            text.contains("vehicle") || text.contains("congestion")) {
            categories.add("TRAFFIC & PARKING");
        }

        // Default category
        if (categories.isEmpty()) {
            categories.add("GENERAL");
        }

        return String.join(", ", categories);
    }


    // =============================
    // MUNICIPAL DEPARTMENT AUTO ASSIGN
    // =============================
    public String detectDepartment(String text) {

        if (text == null || text.isEmpty()) {
            return "General Administration";
        }

        text = text.toLowerCase();

        // ROADS & INFRASTRUCTURE DEPARTMENT
        if (text.contains("pothole") || text.contains("road") || text.contains("street") ||
            text.contains("pavement") || text.contains("footpath") || text.contains("bridge") ||
            text.contains("highway") || text.contains("crack")) {
            return "Roads & Infrastructure";
        }

        // SANITATION & WASTE MANAGEMENT
        if (text.contains("garbage") || text.contains("waste") || text.contains("trash") ||
            text.contains("dirty") || text.contains("sanitation") || text.contains("dump") ||
            text.contains("sweeping") || text.contains("dustbin")) {
            return "Sanitation & Waste Management";
        }

        // STREET LIGHTING & ELECTRICAL
        if (text.contains("light") || text.contains("streetlight") || text.contains("lamp") ||
            text.contains("bulb") || text.contains("dark") || text.contains("lighting")) {
            return "Street Lighting & Electrical";
        }

        // WATER SUPPLY & DRAINAGE
        if (text.contains("water") || text.contains("leak") || text.contains("drain") ||
            text.contains("sewage") || text.contains("pipeline") || text.contains("overflow") ||
            text.contains("waterlog") || text.contains("flood")) {
            return "Water Supply & Drainage";
        }

        // ELECTRICITY DEPARTMENT
        if (text.contains("electric") || text.contains("power") || text.contains("transformer") ||
            text.contains("cable") || text.contains("wire") || text.contains("meter")) {
            return "Electrical Department";
        }

        // HORTICULTURE (Parks & Gardens)
        if (text.contains("park") || text.contains("garden") || text.contains("tree") ||
            text.contains("plant") || text.contains("green") || text.contains("playground")) {
            return "Horticulture & Parks";
        }

        // ANIMAL CONTROL
        if (text.contains("dog") || text.contains("animal") || text.contains("stray") ||
            text.contains("cattle") || text.contains("cow")) {
            return "Animal Control";
        }

        // BUILDING & ENFORCEMENT
        if (text.contains("illegal") || text.contains("encroachment") || 
            text.contains("unauthorized") || text.contains("construction")) {
            return "Building & Enforcement";
        }

        // PUBLIC WORKS
        if (text.contains("toilet") || text.contains("washroom") || text.contains("public")) {
            return "Public Works";
        }

        // TRAFFIC MANAGEMENT
        if (text.contains("traffic") || text.contains("parking") || text.contains("signal")) {
            return "Traffic Management";
        }

        return "General Administration";
    }

    // =============================
    // CALCULATE PRIORITY BASED ON SEVERITY
    // =============================
    public Integer calculatePriority(String severity) {
        if (severity == null) {
            return 3;
        }
        
        return switch (severity.toUpperCase()) {
            case "CRITICAL" -> 1;
            case "HIGH" -> 2;
            case "MEDIUM" -> 3;
            case "LOW" -> 4;
            default -> 3;
        };
    }

    // =============================
    // GET DEPARTMENT CONTACT INFO (for future use)
    // =============================
    public Map<String, String> getDepartmentContact(String department) {
        Map<String, String> contacts = new HashMap<>();
        
        contacts.put("Roads & Infrastructure", "roads@nagarnigam.gov.in");
        contacts.put("Sanitation & Waste Management", "sanitation@nagarnigam.gov.in");
        contacts.put("Street Lighting & Electrical", "lighting@nagarnigam.gov.in");
        contacts.put("Water Supply & Drainage", "water@nagarnigam.gov.in");
        contacts.put("Electrical Department", "electrical@nagarnigam.gov.in");
        contacts.put("Horticulture & Parks", "parks@nagarnigam.gov.in");
        contacts.put("Animal Control", "animals@nagarnigam.gov.in");
        contacts.put("Building & Enforcement", "enforcement@nagarnigam.gov.in");
        contacts.put("Public Works", "publicworks@nagarnigam.gov.in");
        contacts.put("Traffic Management", "traffic@nagarnigam.gov.in");
        contacts.put("General Administration", "general@nagarnigam.gov.in");
        
        return contacts;
    }

    // =============================
    // GET ALL SUPPORTED CATEGORIES
    // =============================
    public List<String> getSupportedCategories() {
        return List.of(
            "ROADS & INFRASTRUCTURE",
            "SANITATION",
            "STREET LIGHTING",
            "WATER & DRAINAGE",
            "ELECTRICITY",
            "PARKS & GARDENS",
            "STRAY ANIMALS",
            "ILLEGAL CONSTRUCTION",
            "PUBLIC FACILITIES",
            "TRAFFIC & PARKING",
            "GENERAL"
        );
    }
}