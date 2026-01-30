package com.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

    // ✅ Vendor ke saare workers laane ke liye
    List<Worker> findByVendorVendorId(Long vendorId);
}
