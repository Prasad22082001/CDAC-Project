package com.exam.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessVendorDTO {

    private Long vendorId;

    @NotBlank
    private String messName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String contact;

    @NotNull
    private Long adminId;
}
