package com.exam.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mess_vendors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessVendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorId;

    private String messName;
    private String email;

    private String password;   // ✅ ADD THIS (IMPORTANT)

    private String type;
    private String contact;

    // ✅ Many vendors belong to one admin
    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    // ✅ Vendor displays many menu items
    @OneToMany(mappedBy = "vendor")
    private List<Menu> menus;

    // ✅ Vendor assigns work to many workers
    @OneToMany(mappedBy = "vendor")
    private List<Worker> workers;
}
