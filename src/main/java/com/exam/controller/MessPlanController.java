package com.exam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.exam.dto.MessPlanDTO;
import com.exam.entity.MessPlan;
import com.exam.service.MessPlanService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/plan")
@CrossOrigin("*")
@AllArgsConstructor
public class MessPlanController {

    private final MessPlanService planService;

    // ADD PLAN (vendor ke under)
    @PostMapping("/add/{vendorId}")
    public ResponseEntity<MessPlanDTO> addPlan(
            @RequestBody MessPlan plan,
            @PathVariable Long vendorId) {

        return ResponseEntity.ok(planService.addPlan(plan, vendorId));
    }

    // GET ALL PLANS
    @GetMapping("/all")
    public ResponseEntity<List<MessPlanDTO>> getAllPlans() {
        return ResponseEntity.ok(planService.getAllPlans());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<MessPlanDTO> getPlan(@PathVariable Long id) {
        return ResponseEntity.ok(planService.getPlanById(id));
    }

    // UPDATE PLAN
    @PutMapping("/update/{id}")
    public ResponseEntity<MessPlanDTO> updatePlan(
            @PathVariable Long id,
            @RequestBody MessPlan plan) {

        return ResponseEntity.ok(planService.updatePlan(id, plan));
    }

    // DELETE PLAN
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return ResponseEntity.ok("Plan deleted successfully");
    }
}
