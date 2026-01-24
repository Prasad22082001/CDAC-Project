package com.exam.service;

import java.util.List;
import com.exam.dto.StudentDTO;
import com.exam.entity.Student;

public interface StudentService {

    StudentDTO addStudent(Student student);

    List<StudentDTO> getAllStudents();

    StudentDTO getStudentById(Long id);

    StudentDTO updateStudent(Long id, Student student);

    void deleteStudent(Long id);
}
