package com.exam.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.MessPlanDTO;
import com.exam.entity.Admin;
import com.exam.entity.MessPlan;
import com.exam.repository.AdminRepository;
import com.exam.repository.MessPlanRepository;
import com.exam.service.MessPlanService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessPlanServiceImpl implements MessPlanService {

    private final MessPlanRepository planRepository;
    private final AdminRepository adminRepository;
    private final ModelMapper mapper;

    @Override
    public MessPlanDTO addPlan(MessPlanDTO dto) {

        // 🔗 resolve adminId
        Admin admin = adminRepository.findById(dto.getAdminId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // DTO → Entity
        MessPlan plan = mapper.map(dto, MessPlan.class);
        plan.setAdmin(admin);

        // save
        MessPlan saved = planRepository.save(plan);

        // Entity → DTO
        return mapper.map(saved, MessPlanDTO.class);
    }

    @Override
    public MessPlanDTO getPlanById(Long id) {

        MessPlan plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        return mapper.map(plan, MessPlanDTO.class);
    }

    @Override
    public List<MessPlanDTO> getAllPlans() {

        return planRepository.findAll()
                .stream()
                .map(p -> mapper.map(p, MessPlanDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deletePlan(Long id) {

        MessPlan plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        planRepository.delete(plan);
    }
}
