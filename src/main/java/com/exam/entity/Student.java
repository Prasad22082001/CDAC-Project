package com.exam.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private String password;

    // ✅ selected mess plan (optional)
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private MessPlan plan;

    // ✅ selected mess vendor (NEW)
    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private MessVendor selectedVendor;
}
