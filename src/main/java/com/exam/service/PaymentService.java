package com.exam.service;

import java.util.List;
import com.exam.dto.PaymentDTO;

public interface PaymentService {

    
    PaymentDTO makePayment(PaymentDTO dto, Long studentId);

 
    List<PaymentDTO> getMyPayments(Long studentId);

  
    List<PaymentDTO> getAllPayments();
}
