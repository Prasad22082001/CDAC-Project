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

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;
    private StudentRepository studentRepository;
    private ModelMapper mapper;

    @Override
    public PaymentDTO addPayment(PaymentDTO dto) {

        // get student
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // map dto to entity
        Payment payment = mapper.map(dto, Payment.class);
        payment.setStudent(student);
        payment.setPaymentDate(LocalDateTime.now());

        // save
        Payment saved = paymentRepository.save(payment);

        return mapper.map(saved, PaymentDTO.class);
    }

    @Override
    public List<PaymentDTO> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(p -> mapper.map(p, PaymentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deletePayment(Long id) {

        paymentRepository.deleteById(id);
    }
}
