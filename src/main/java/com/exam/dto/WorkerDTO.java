package com.exam.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerDTO {

    private Long workerId; // response ke liye

    @NotBlank(message = "Worker name cannot be blank")
    private String name;

    @NotBlank(message = "Worker role is required")
    private String role;   // cook / cleaner / helper

    @NotBlank(message = "Contact number is required")
    @Pattern(
        regexp = "^[6-9][0-9]{9}$",
        message = "Contact must be a valid 10-digit number"
    )
    private String contact;

    @NotNull(message = "Vendor ID is required")
    private Long vendorId;   // in-between use
}
