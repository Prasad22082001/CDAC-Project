package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.exam.entity.MessPlan;

public interface MessPlanRepository extends JpaRepository<MessPlan, Long> {
}
