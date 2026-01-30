package com.exam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Student;
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email); // ✅ ADD THIS
}
