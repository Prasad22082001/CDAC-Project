package com.exam.service;

import java.util.List;
import com.exam.dto.PaymentDTO;

public interface PaymentService {

    PaymentDTO addPayment(PaymentDTO dto);

    List<PaymentDTO> getAllPayments();

    void deletePayment(Long id);
}
