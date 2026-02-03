package com.exam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.MessPlanDTO;
import com.exam.service.MessPlanService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
@RestController
@RequestMapping("/plan")
@CrossOrigin("*")
@AllArgsConstructor
public class MessPlanController {

    private final MessPlanService planService;

    // ➕ ADD PLAN → ADMIN ONLY
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<MessPlanDTO> addPlan(
            @Valid @RequestBody MessPlanDTO dto) {

        return ResponseEntity.ok(planService.addPlan(dto));
    }

    // 👀 GET PLAN BY ID → ADMIN + STUDENT
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<MessPlanDTO> getPlan(@PathVariable Long id) {
        return ResponseEntity.ok(planService.getPlanById(id));
    }

    // 👀 GET ALL PLANS → ADMIN + STUDENT
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("/all")
    public ResponseEntity<List<MessPlanDTO>> getAllPlans() {
        return ResponseEntity.ok(planService.getAllPlans());
    }

    // ❌ DELETE PLAN → ADMIN ONLY
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePlan(@PathVariable Long id) {

        planService.deletePlan(id);
        return ResponseEntity.ok("Mess plan deleted successfully");
    }
}
