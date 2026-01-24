package com.exam.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mess_plans")
public class MessPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    private String planName;   // Monthly / Weekly
    private double price;      // amount
    private int durationDays;  // 30 / 7

    // 🔗 mapping: many plans belong to one vendor
    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private MessVendor vendor;
}
