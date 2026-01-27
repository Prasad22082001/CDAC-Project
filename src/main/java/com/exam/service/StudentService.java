package com.exam.service;

import java.util.List;
import com.exam.dto.StudentDTO;

public interface StudentService {

    StudentDTO addStudent(StudentDTO dto);

    StudentDTO getStudentById(Long id);

    List<StudentDTO> getAllStudents();

    void deleteStudent(Long id);
}
