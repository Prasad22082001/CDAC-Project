package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.entity.MessPlan;

@Repository
public interface MessPlanRepository extends JpaRepository<MessPlan, Long> {
    // No extra methods required for basic CRUD
}
