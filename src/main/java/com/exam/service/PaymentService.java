package com.exam.service;

import java.util.List;
import com.exam.dto.PaymentDTO;

public interface PaymentService {

    // 💳 STUDENT → MAKE PAYMENT
    PaymentDTO makePayment(Long studentId, PaymentDTO dto);

    // 👀 STUDENT → OWN PAYMENTS
    List<PaymentDTO> getMyPayments(Long studentId);

    // 👑 ADMIN → ALL PAYMENTS
    List<PaymentDTO> getAllPayments();
}
