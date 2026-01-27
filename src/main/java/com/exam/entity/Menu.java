package com.exam.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "menu")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @NotBlank(message = "Item name cannot be empty")
    private String itemName;

    @Positive(message = "Price must be greater than zero")
    private double price;

    @NotNull(message = "Vendor is required")
    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private MessVendor vendor;
}
