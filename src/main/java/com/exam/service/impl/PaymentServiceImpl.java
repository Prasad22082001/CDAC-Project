package com.exam.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.PaymentDTO;
import com.exam.entity.Payment;
import com.exam.entity.Student;
import com.exam.repository.PaymentRepository;
import com.exam.repository.StudentRepository;
import com.exam.service.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    @Override
    public PaymentDTO makePayment(PaymentDTO dto, Long studentId) {

        // 🔗 validate student
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // DTO → Entity
        Payment payment = mapper.map(dto, Payment.class);
        payment.setStudent(student);
        payment.setPaymentDate(LocalDateTime.now());

        // 💳 MOCK PAYMENT FLOW
        payment.setStatus("SUCCESS"); // always success for project

        Payment saved = paymentRepository.save(payment);
        return mapper.map(saved, PaymentDTO.class);
    }

    @Override
    public List<PaymentDTO> getMyPayments(Long studentId) {

        return paymentRepository.findByStudentStudentId(studentId)
                .stream()
                .map(payment -> mapper.map(payment, PaymentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(payment -> mapper.map(payment, PaymentDTO.class))
                .collect(Collectors.toList());
    }
}
