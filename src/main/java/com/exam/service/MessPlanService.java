package com.exam.service;

import java.util.List;
<<<<<<< HEAD
=======

>>>>>>> c942c4eb9ebf57e076cad6b0a854e31dd4cb4643
import com.exam.dto.MessPlanDTO;
import com.exam.entity.MessPlan;

public interface MessPlanService {

<<<<<<< HEAD
    MessPlanDTO addPlan(MessPlan plan, Long vendorId);

    List<MessPlanDTO> getAllPlans();

    MessPlanDTO getPlanById(Long id);

    MessPlanDTO updatePlan(Long id, MessPlan plan);

    void deletePlan(Long id);
=======
    MessPlan createMessPlan(MessPlanDTO dto);

    List<MessPlan> getAllMessPlans();

    MessPlan getMessPlanById(Long id);

    MessPlan updateMessPlan(Long id, MessPlanDTO dto);

    void deleteMessPlan(Long id);
>>>>>>> c942c4eb9ebf57e076cad6b0a854e31dd4cb4643
}
