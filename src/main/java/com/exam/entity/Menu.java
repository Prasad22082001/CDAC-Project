package com.exam.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "menus")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @NotBlank
    private String itemName;

    @Enumerated(EnumType.STRING)
    @NotNull
    private FoodType type;

    @Positive
    private double price;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private MessVendor vendor;
}
