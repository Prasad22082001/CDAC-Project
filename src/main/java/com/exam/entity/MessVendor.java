package com.exam.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

