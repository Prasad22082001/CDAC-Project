package com.exam.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.StudentDTO;
import com.exam.entity.MessPlan;
import com.exam.entity.Student;
import com.exam.repository.MessPlanRepository;
import com.exam.repository.StudentRepository;
import com.exam.service.StudentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final MessPlanRepository planRepository;
    private final ModelMapper mapper;

    @Override
    public StudentDTO addStudent(StudentDTO dto) {

        // 🔗 resolve planId
        MessPlan plan = planRepository.findById(dto.getPlanId())
                .orElseThrow(() -> new RuntimeException("Mess plan not found"));

        // DTO → Entity
        Student student = mapper.map(dto, Student.class);
        student.setPlan(plan);

        // save
        Student saved = studentRepository.save(student);

        // Entity → DTO
        return mapper.map(saved, StudentDTO.class);
    }

    @Override
    public StudentDTO getStudentById(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return mapper.map(student, StudentDTO.class);
    }

    @Override
    public List<StudentDTO> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(s -> mapper.map(s, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteStudent(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        studentRepository.delete(student);
    }
}
