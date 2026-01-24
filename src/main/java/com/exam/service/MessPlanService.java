package com.exam.service;

import java.util.List;

import com.exam.dto.MessPlanDTO;
import com.exam.entity.MessPlan;

public interface MessPlanService {

    MessPlan createMessPlan(MessPlanDTO dto);

    List<MessPlan> getAllMessPlans();

    MessPlan getMessPlanById(Long id);

    MessPlan updateMessPlan(Long id, MessPlanDTO dto);

    void deleteMessPlan(Long id);
}
