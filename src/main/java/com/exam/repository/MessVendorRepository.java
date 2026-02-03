package com.exam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.MessVendor;

public interface MessVendorRepository
        extends JpaRepository<MessVendor, Long> {

    Optional<MessVendor> findByEmail(String email);
}
	