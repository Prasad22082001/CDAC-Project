package com.exam.service;

import java.util.List;
import com.exam.dto.MessPlanDTO;

public interface MessPlanService {

    MessPlanDTO addPlan(MessPlanDTO dto);

    MessPlanDTO getPlanById(Long id);

    List<MessPlanDTO> getAllPlans();

    void deletePlan(Long id);
}
