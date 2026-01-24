package com.exam.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    private String itemName;   // Dal, Rice, Paneer
    private String type;       // Veg / Non-Veg
    private double price;

    // 🔗 mapping: many menu items belong to one vendor
    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private MessVendor vendor;
}
