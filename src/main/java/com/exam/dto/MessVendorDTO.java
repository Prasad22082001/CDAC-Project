package com.exam.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessVendorDTO {

    private Long vendorId; // response ke liye

    @NotBlank(message = "Mess name cannot be blank")
    private String messName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;   // ✅ ADD THIS

    @NotBlank(message = "Contact number is required")
    private String contact;

    @NotNull(message = "Admin ID is required")
    private Long adminId;

    // optional
    // private String type;
}
