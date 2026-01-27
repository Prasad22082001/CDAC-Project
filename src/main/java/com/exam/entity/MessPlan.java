package com.exam.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mess_plans")
public class MessPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long planId;

    @Column(nullable = false)
    private String planName;   // Monthly / Weekly

    @Column(nullable = false)
    private double price;      // amount

    @Column(nullable = false)
    private int durationDays;  // 30 / 7

    // ✅ Many plans are managed by one admin
    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    // ✅ One plan can be chosen by many students
    @OneToMany(mappedBy = "plan")
    private List<Student> students;
}
