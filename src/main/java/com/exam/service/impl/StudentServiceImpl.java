package com.exam.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exam.dto.StudentDTO;
import com.exam.entity.MessPlan;
import com.exam.entity.MessVendor;
import com.exam.entity.Student;
import com.exam.repository.MessPlanRepository;
import com.exam.repository.MessVendorRepository;
import com.exam.repository.StudentRepository;
import com.exam.service.StudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final MessPlanRepository planRepository;
    private final MessVendorRepository vendorRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public StudentDTO addStudent(StudentDTO dto) {
        Student student = mapper.map(dto, Student.class);
        student.setPassword(passwordEncoder.encode(dto.getPassword()));
        return mapper.map(studentRepository.save(student), StudentDTO.class);
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        StudentDTO dto = mapper.map(student, StudentDTO.class);

        if (student.getPlan() != null)
            dto.setPlanId(student.getPlan().getPlanId());

        if (student.getSelectedVendor() != null)
            dto.setVendorId(student.getSelectedVendor().getVendorId());

        return dto;
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private StudentDTO mapToDTO(Student student) {
        StudentDTO dto = mapper.map(student, StudentDTO.class);

        if (student.getPlan() != null)
            dto.setPlanId(student.getPlan().getPlanId());

        if (student.getSelectedVendor() != null)
            dto.setVendorId(student.getSelectedVendor().getVendorId());

        return dto;
    }

    @Override
    public StudentDTO selectPlan(Long studentId, Long planId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        MessPlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        student.setPlan(plan);
        return mapToDTO(studentRepository.save(student));
    }

    // ✅ NEW FEATURE
    @Override
    public StudentDTO selectVendor(Long studentId, Long vendorId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        MessVendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        student.setSelectedVendor(vendor);
        return mapToDTO(studentRepository.save(student));
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
 // ================= VENDOR → SEE OWN STUDENTS =================
    @Override
    public List<StudentDTO> getStudentsByVendor(Long vendorId) {

        MessVendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        return studentRepository.findBySelectedVendor(vendor)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

}
