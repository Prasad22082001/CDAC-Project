package com.exam.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessPlanDTO {

    private Long planId;
    private String planName;
    private double price;
    private int durationDays;
}
