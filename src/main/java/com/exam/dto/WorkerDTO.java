package com.exam.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerDTO {

    private Long workerId;

    @NotBlank
    private String name;

    @NotBlank
    private String role;

    @NotBlank
    @Pattern(
        regexp = "^[6-9][0-9]{9}$",
        message = "Contact must be a valid 10-digit number"
    )
    private String contact;

    @NotNull
    private Long vendorId;
}
