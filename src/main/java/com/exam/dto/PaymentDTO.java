package com.exam.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {

    private Long paymentId;
    private double amount;
    private String paymentMode;
    private String status;
    private LocalDateTime paymentDate;
}
