package com.exam.service;

import java.util.List;
import com.exam.dto.MessPlanDTO;
import com.exam.entity.MessPlan;

public interface MessPlanService {

    MessPlanDTO addPlan(MessPlan plan, Long vendorId);

    List<MessPlanDTO> getAllPlans();

    MessPlanDTO getPlanById(Long id);

    MessPlanDTO updatePlan(Long id, MessPlan plan);

    void deletePlan(Long id);
}
