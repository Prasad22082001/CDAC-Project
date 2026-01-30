package com.exam.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exam.dto.StudentDTO;
import com.exam.entity.MessPlan;
import com.exam.entity.Student;
import com.exam.repository.MessPlanRepository;
import com.exam.repository.StudentRepository;
import com.exam.service.StudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final MessPlanRepository planRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public StudentDTO addStudent(StudentDTO dto) {

        Student student = mapper.map(dto, Student.class);
        student.setPassword(passwordEncoder.encode(dto.getPassword()));

        Student saved = studentRepository.save(student);
        return mapper.map(saved, StudentDTO.class);
    }

    @Override
    public StudentDTO getStudentById(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        StudentDTO dto = mapper.map(student, StudentDTO.class);
        if (student.getPlan() != null) {
            dto.setPlanId(student.getPlan().getPlanId());
        }
        return dto;
    }

    @Override
    public List<StudentDTO> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(student -> {
                    StudentDTO dto = mapper.map(student, StudentDTO.class);
                    if (student.getPlan() != null) {
                        dto.setPlanId(student.getPlan().getPlanId());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // ✅ STUDENT SELECT PLAN
    @Override
    public StudentDTO selectPlan(Long studentId, Long planId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        MessPlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Mess plan not found"));

        student.setPlan(plan);
        Student saved = studentRepository.save(student);

        StudentDTO dto = mapper.map(saved, StudentDTO.class);
        dto.setPlanId(planId);
        return dto;
    }

    @Override
    public void deleteStudent(Long id) {

        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found");
        }
        studentRepository.deleteById(id);
    }
}
