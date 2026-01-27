package com.exam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // ✅ ADD PLAN
    @PostMapping("/add")
    public ResponseEntity<MessPlanDTO> addPlan(
            @Valid @RequestBody MessPlanDTO dto) {

        return ResponseEntity.ok(planService.addPlan(dto));
    }

    // ✅ GET PLAN BY ID
    @GetMapping("/{id}")
    public ResponseEntity<MessPlanDTO> getPlan(@PathVariable Long id) {

        return ResponseEntity.ok(planService.getPlanById(id));
    }

    // ✅ GET ALL PLANS
    @GetMapping("/all")
    public ResponseEntity<List<MessPlanDTO>> getAllPlans() {

        return ResponseEntity.ok(planService.getAllPlans());
    }

    // ✅ DELETE PLAN
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePlan(@PathVariable Long id) {

        planService.deletePlan(id);
        return ResponseEntity.ok("Mess plan deleted successfully");
    }
}
