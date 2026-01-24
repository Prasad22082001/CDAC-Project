package com.exam.service.impl;

<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> c942c4eb9ebf57e076cad6b0a854e31dd4cb4643
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.MessPlanDTO;
<<<<<<< HEAD
import com.exam.entity.MessPlan;
import com.exam.entity.MessVendor;
import com.exam.repository.MessPlanRepository;
import com.exam.repository.MessVendorRepository;
=======
import com.exam.entity.Admin;
import com.exam.entity.MessPlan;
import com.exam.repository.AdminRepository;
import com.exam.repository.MessPlanRepository;
>>>>>>> c942c4eb9ebf57e076cad6b0a854e31dd4cb4643
import com.exam.service.MessPlanService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessPlanServiceImpl implements MessPlanService {

<<<<<<< HEAD
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
=======
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
>>>>>>> c942c4eb9ebf57e076cad6b0a854e31dd4cb4643
    }
}
