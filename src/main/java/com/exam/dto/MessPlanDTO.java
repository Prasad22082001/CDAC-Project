package com.exam.dto;

<<<<<<< HEAD
import lombok.Getter;
=======
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
>>>>>>> c942c4eb9ebf57e076cad6b0a854e31dd4cb4643
import lombok.Setter;

@Getter
@Setter
<<<<<<< HEAD
public class MessPlanDTO {

    private Long planId;
    private String planName;
    private double price;
    private int durationDays;
=======
@NoArgsConstructor
@AllArgsConstructor
public class MessPlanDTO {

    private Long planId;
    private String messName;
    private Double charges;
    private Integer duration;

    // FK reference
    private Long adminId;
>>>>>>> c942c4eb9ebf57e076cad6b0a854e31dd4cb4643
}
