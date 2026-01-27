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

    @NotBlank(message = "Item name cannot be blank")
    private String itemName;

    @NotNull(message = "Food type is required")
    private FoodType type;

    @Positive(message = "Price must be greater than zero")
    private double price;

    @NotNull(message = "Vendor ID is required")
    private Long vendorId;
}
