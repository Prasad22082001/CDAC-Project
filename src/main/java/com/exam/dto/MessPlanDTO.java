package com.exam.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessPlanDTO {

    private Long planId; // response ke liye

    @NotBlank(message = "Plan name cannot be blank")
    private String planName;

    @Positive(message = "Price must be greater than zero")
    private double price;

    @Positive(message = "Duration must be greater than zero")
    private int durationDays;

    @NotNull(message = "Admin ID is required")
    private Long adminId;   // in-between use
}
