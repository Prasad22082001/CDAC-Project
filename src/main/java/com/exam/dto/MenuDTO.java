package com.exam.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuDTO {

    private Long menuId;
    private String itemName;
    private String type;
    private double price;
}
