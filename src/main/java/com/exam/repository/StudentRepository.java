package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.exam.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
