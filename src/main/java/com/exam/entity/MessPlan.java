package com.exam.entity;

import jakarta.persistence.*;
import lombok.*;

<<<<<<< HEAD
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mess_plans")
=======
@Entity
@Table(name = "mess_plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
>>>>>>> c942c4eb9ebf57e076cad6b0a854e31dd4cb4643
public class MessPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

<<<<<<< HEAD
    private String planName;   // Monthly / Weekly
    private double price;      // amount
    private int durationDays;  // 30 / 7

    // 🔗 mapping: many plans belong to one vendor
    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private MessVendor vendor;
=======
    private String messName;
    private Double charges;
    private Integer duration;

    // FK -> admins.admin_id
    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "adminId", nullable = false)
    private Admin admin;
>>>>>>> c942c4eb9ebf57e076cad6b0a854e31dd4cb4643
}
