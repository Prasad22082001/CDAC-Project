package com.exam.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import com.exam.dto.MessPlanDTO;
import com.exam.entity.Admin;
import com.exam.entity.MessPlan;
import com.exam.repository.AdminRepository;
import com.exam.repository.MessPlanRepository;

import lombok.AllArgsConstructor;

@RestController
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
    }
}
