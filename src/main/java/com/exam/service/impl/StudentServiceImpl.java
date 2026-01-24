package com.exam.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.StudentDTO;
import com.exam.entity.Student;
import com.exam.repository.StudentRepository;
import com.exam.service.StudentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    public StudentDTO addStudent(Student student) {
        Student saved = studentRepository.save(student);
        return modelMapper.map(saved, StudentDTO.class);
    }

    @Override
    public List<StudentDTO> getAllStudents() {

        List<Student> students = studentRepository.findAll();
        List<StudentDTO> list = new ArrayList<>();

        for (Student s : students) {
            list.add(modelMapper.map(s, StudentDTO.class));
        }
        return list;
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return modelMapper.map(student, StudentDTO.class);
    }

    @Override
    public StudentDTO updateStudent(Long id, Student student) {

        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        existing.setName(student.getName());
        existing.setEmail(student.getEmail());
        existing.setContact(student.getContact());
        existing.setPassword(student.getPassword());

        Student updated = studentRepository.save(existing);
        return modelMapper.map(updated, StudentDTO.class);
    }


    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
