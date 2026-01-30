package com.exam.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO {

    private Long studentId;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String contact;

    @NotBlank
    @Size(min = 6)
    private String password;

    // optional (select later)
    private Long planId;
}
