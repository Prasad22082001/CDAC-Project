package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Admin;

public interface AdminRepository  extends JpaRepository<Admin, Long>{

}
