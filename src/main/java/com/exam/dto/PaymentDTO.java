package com.exam.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {

    private Long paymentId; // response ke liye

    @Positive(message = "Amount must be greater than zero")
    private double amount;

    @NotBlank(message = "Payment mode is required")
    private String paymentMode;   // UPI / CASH / CARD

    @NotBlank(message = "Payment status is required")
    private String status;        // SUCCESS / FAILED

    @NotNull(message = "Student ID is required")
    private Long studentId;       // in-between use

    @NotNull(message = "Admin ID is required")
    private Long adminId;         // in-between use
}
