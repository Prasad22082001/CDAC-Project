package com.exam.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mess_vendors")
public class MessVendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorId;

    private String messName;
    private String email;
    private String type;      // veg / non-veg
    private String contact;

    // 🔗 Mapping: many vendors belong to one admin
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;
}
