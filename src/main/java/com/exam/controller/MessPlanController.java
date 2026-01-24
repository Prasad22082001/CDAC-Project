package com.exam.controller;

import java.util.List;

<<<<<<< HEAD
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.exam.dto.MessPlanDTO;
import com.exam.entity.MessPlan;
import com.exam.service.MessPlanService;
=======
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import com.exam.dto.MessPlanDTO;
import com.exam.entity.Admin;
import com.exam.entity.MessPlan;
import com.exam.repository.AdminRepository;
import com.exam.repository.MessPlanRepository;
>>>>>>> c942c4eb9ebf57e076cad6b0a854e31dd4cb4643

import lombok.AllArgsConstructor;

@RestController
<<<<<<< HEAD
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
=======
@RequestMapping("/api/mess-plans")
@AllArgsConstructor
public class MessPlanController {

    private final MessPlanRepository messPlanRepository;
    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;

    // CREATE Mess Plan
    @PostMapping
    public MessPlan createMessPlan(@RequestBody MessPlanDTO dto) {

        MessPlan plan = modelMapper.map(dto, MessPlan.class);

        Admin admin = adminRepository.findById(dto.getAdminId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        plan.setAdmin(admin);

        return messPlanRepository.save(plan);
    }

    // GET all Mess Plans
    @GetMapping
    public List<MessPlan> getAllMessPlans() {
        return messPlanRepository.findAll();
    }

    // GET Mess Plan by ID
    @GetMapping("/{id}")
    public MessPlan getMessPlanById(@PathVariable Long id) {
        return messPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mess Plan not found"));
    }

    // UPDATE Mess Plan
    @PutMapping("/{id}")
    public MessPlan updateMessPlan(
            @PathVariable Long id,
            @RequestBody MessPlanDTO dto) {

        MessPlan existing = messPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mess Plan not found"));

        // maps only non-null fields because of Conditions.isNotNull()
        modelMapper.map(dto, existing);

        Admin admin = adminRepository.findById(dto.getAdminId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        existing.setAdmin(admin);

        return messPlanRepository.save(existing);
    }

    // DELETE Mess Plan
    @DeleteMapping("/{id}")
    public void deleteMessPlan(@PathVariable Long id) {
        messPlanRepository.deleteById(id);
>>>>>>> c942c4eb9ebf57e076cad6b0a854e31dd4cb4643
    }
}
