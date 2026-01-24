package com.exam.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessVendorDTO {

    private Long vendorId;
    private String messName;
    private String email;
    private String type;
    private String contact;
}
