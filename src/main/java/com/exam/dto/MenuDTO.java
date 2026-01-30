package com.exam.dto;

import com.exam.entity.FoodType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuDTO {

    private Long menuId;

    @NotBlank
    private String itemName;

    @NotNull
    private FoodType type;

    @Positive
    private double price;

    @NotNull
    private Long vendorId;
}
