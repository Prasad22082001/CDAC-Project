package com.exam.service;

import java.util.List;
import com.exam.dto.PaymentDTO;
import com.exam.entity.Payment;

public interface PaymentService {

    PaymentDTO makePayment(Payment payment, Long studentId, Long planId);

    List<PaymentDTO> getAllPayments();

    PaymentDTO getPaymentById(Long id);
}
