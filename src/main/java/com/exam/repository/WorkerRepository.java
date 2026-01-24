package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.exam.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
}
