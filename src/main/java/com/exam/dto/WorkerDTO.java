package com.exam.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerDTO {

    private Long workerId;

    @NotBlank(message = "Worker name is required")
    private String name;

    @NotBlank(message = "Worker role is required")
    private String role;   // cook / cleaner / helper

    @NotBlank(message = "Contact is required")
    @Pattern(
        regexp = "^[6-9][0-9]{9}$",
        message = "Contact must be a valid 10-digit number"
    )
    private String contact;

    // ✅ RESPONSE ONLY (never from frontend)
    private Long vendorId;
}
