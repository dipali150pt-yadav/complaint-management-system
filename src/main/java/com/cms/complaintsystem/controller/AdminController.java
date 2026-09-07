package com.cms.complaintsystem.controller;

import com.cms.complaintsystem.model.Complaint;
import com.cms.complaintsystem.repository.ComplaintRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    ComplaintRepository repository;

    // Admin Dashboard with Enhanced Analytics
    @GetMapping("/admin")
    public String adminDashboard(Model model) {

        List<Complaint> complaints = repository.findAll();

        int total = complaints.size();

        long pending = complaints.stream()
                .filter(c -> "Pending".equals(c.getStatus()))
                .count();

        long resolved = complaints.stream()
                .filter(c -> "Resolved".equals(c.getStatus()))
                .count();

        // Calculate statistics by severity
        Map<String, Long> bySeverity = complaints.stream()
                .filter(c -> c.getSeverity() != null)
                .collect(Collectors.groupingBy(Complaint::getSeverity, Collectors.counting()));

        // Calculate statistics by department
        Map<String, Long> byDepartment = complaints.stream()
                .filter(c -> c.getDepartment() != null)
                .collect(Collectors.groupingBy(Complaint::getDepartment, Collectors.counting()));

        // Calculate statistics by category
        Map<String, Long> byCategory = complaints.stream()
                .filter(c -> c.getCategory() != null)
                .collect(Collectors.groupingBy(Complaint::getCategory, Collectors.counting()));

        // Calculate average AI confidence
        double avgConfidence = complaints.stream()
                .filter(c -> c.getAiConfidence() != null)
                .mapToDouble(Complaint::getAiConfidence)
                .average()
                .orElse(0.0);

        // Count high priority complaints
        long highPriority = complaints.stream()
                .filter(c -> c.getPriority() != null && c.getPriority() <= 2)
                .count();

        model.addAttribute("complaints", complaints);
        model.addAttribute("total", total);
        model.addAttribute("pending", pending);
        model.addAttribute("resolved", resolved);
        model.addAttribute("bySeverity", bySeverity);
        model.addAttribute("byDepartment", byDepartment);
        model.addAttribute("byCategory", byCategory);
        model.addAttribute("avgConfidence", String.format("%.0f%%", avgConfidence * 100));
        model.addAttribute("highPriority", highPriority);

        return "admin";
    }

    // Resolve Complaint with timestamp
    @GetMapping("/resolve/{id}")
    public String resolveComplaint(@PathVariable Long id) {

        Complaint complaint = repository.findById(id).orElse(null);

        if (complaint != null) {
            complaint.setStatus("Resolved");
            complaint.setResolvedAt(LocalDateTime.now());
            repository.save(complaint);
        }

        return "redirect:/admin";
    }

    // Delete Complaint
    @GetMapping("/delete/{id}")
    public String deleteComplaint(@PathVariable Long id) {

        repository.deleteById(id);

        return "redirect:/admin";
    }
}