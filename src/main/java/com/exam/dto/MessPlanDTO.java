package com.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessPlanDTO {

    private Long planId;
    private String messName;
    private Double charges;
    private Integer duration;

    // FK reference
    private Long adminId;
}
