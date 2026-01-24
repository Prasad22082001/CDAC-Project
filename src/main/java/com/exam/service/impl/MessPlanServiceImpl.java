package com.exam.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.MessPlanDTO;
import com.exam.entity.MessPlan;
import com.exam.entity.MessVendor;
import com.exam.repository.MessPlanRepository;
import com.exam.repository.MessVendorRepository;
import com.exam.service.MessPlanService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessPlanServiceImpl implements MessPlanService {

    private final MessPlanRepository planRepository;
    private final MessVendorRepository vendorRepository;
    private final ModelMapper modelMapper;

    @Override
    public MessPlanDTO addPlan(MessPlan plan, Long vendorId) {

        MessVendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        plan.setVendor(vendor); // 🔗 mapping
        MessPlan saved = planRepository.save(plan);

        return modelMapper.map(saved, MessPlanDTO.class);
    }

    @Override
    public List<MessPlanDTO> getAllPlans() {

        List<MessPlan> plans = planRepository.findAll();
        List<MessPlanDTO> list = new ArrayList<>();

        for (MessPlan p : plans) {
            list.add(modelMapper.map(p, MessPlanDTO.class));
        }
        return list;
    }

    @Override
    public MessPlanDTO getPlanById(Long id) {

        MessPlan plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        return modelMapper.map(plan, MessPlanDTO.class);
    }

    @Override
    public MessPlanDTO updatePlan(Long id, MessPlan plan) {

        MessPlan existing = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        existing.setPlanName(plan.getPlanName());
        existing.setPrice(plan.getPrice());
        existing.setDurationDays(plan.getDurationDays());

        MessPlan updated = planRepository.save(existing);
        return modelMapper.map(updated, MessPlanDTO.class);
    }

    @Override
    public void deletePlan(Long id) {
        planRepository.deleteById(id);
    }
}
