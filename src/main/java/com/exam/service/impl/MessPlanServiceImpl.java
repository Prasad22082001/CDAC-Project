package com.exam.service.impl;

import java.util.List;

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

    private final MessPlanRepository messPlanRepository;
    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;

    @Override
    public MessPlan createMessPlan(MessPlanDTO dto) {

        MessPlan plan = modelMapper.map(dto, MessPlan.class);

        Admin admin = adminRepository.findById(dto.getAdminId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        plan.setAdmin(admin);

        return messPlanRepository.save(plan);
    }

    @Override
    public List<MessPlan> getAllMessPlans() {
        return messPlanRepository.findAll();
    }

    @Override
    public MessPlan getMessPlanById(Long id) {
        return messPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mess Plan not found"));
    }

    @Override
    public MessPlan updateMessPlan(Long id, MessPlanDTO dto) {

        MessPlan existing = messPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mess Plan not found"));

        // maps only non-null fields (because of Conditions.isNotNull)
        modelMapper.map(dto, existing);

        if (dto.getAdminId() != null) {
            Admin admin = adminRepository.findById(dto.getAdminId())
                    .orElseThrow(() -> new RuntimeException("Admin not found"));
            existing.setAdmin(admin);
        }

        return messPlanRepository.save(existing);
    }

    @Override
    public void deleteMessPlan(Long id) {
        messPlanRepository.deleteById(id);
    }
}
