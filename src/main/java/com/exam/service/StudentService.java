package com.exam.service;

import java.util.List;
import com.exam.dto.StudentDTO;

public interface StudentService {

    StudentDTO addStudent(StudentDTO dto);

    StudentDTO getStudentById(Long id);

    List<StudentDTO> getAllStudents(); // ✅ ADMIN

    StudentDTO selectPlan(Long studentId, Long planId);

    StudentDTO selectVendor(Long studentId, Long vendorId);

    void deleteStudent(Long id);
    
 // ✅ VENDOR → SEE OWN STUDENTS
    List<StudentDTO> getStudentsByVendor(Long vendorId);

}
