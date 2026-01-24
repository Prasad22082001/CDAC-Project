package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import com.exam.entity.MessPlan;

public interface MessPlanRepository extends JpaRepository<MessPlan, Long> {
=======
import org.springframework.stereotype.Repository;

import com.exam.entity.MessPlan;

@Repository
public interface MessPlanRepository extends JpaRepository<MessPlan, Long> {
    // No extra methods required for basic CRUD
>>>>>>> c942c4eb9ebf57e076cad6b0a854e31dd4cb4643
}
