package com.exam.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {

    private Long paymentId;

    @Positive(message = "Amount must be greater than zero")
    private double amount;

    @NotBlank(message = "Payment mode is required")
    private String paymentMode;   // UPI / CASH / CARD

    private String status;        // SUCCESS / FAILED

    private LocalDateTime paymentDate;
}
