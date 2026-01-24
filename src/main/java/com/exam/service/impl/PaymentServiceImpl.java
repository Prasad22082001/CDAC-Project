package com.exam.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.PaymentDTO;
import com.exam.entity.MessPlan;
import com.exam.entity.Payment;
import com.exam.entity.Student;
import com.exam.repository.MessPlanRepository;
import com.exam.repository.PaymentRepository;
import com.exam.repository.StudentRepository;
import com.exam.service.PaymentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final MessPlanRepository planRepository;
    private final ModelMapper modelMapper;

    @Override
    public PaymentDTO makePayment(Payment payment, Long studentId, Long planId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        MessPlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        payment.setStudent(student);
        payment.setPlan(plan);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus("SUCCESS");

        Payment saved = paymentRepository.save(payment);
        return modelMapper.map(saved, PaymentDTO.class);
    }

    @Override
    public List<PaymentDTO> getAllPayments() {

        List<Payment> payments = paymentRepository.findAll();
        List<PaymentDTO> list = new ArrayList<>();

        for (Payment p : payments) {
            list.add(modelMapper.map(p, PaymentDTO.class));
        }
        return list;
    }

    @Override
    public PaymentDTO getPaymentById(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        return modelMapper.map(payment, PaymentDTO.class);
    }
}
