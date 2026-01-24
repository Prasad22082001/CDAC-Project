package com.exam.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mess_plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    private String messName;
    private Double charges;
    private Integer duration;

    // FK -> admins.admin_id
    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "adminId", nullable = false)
    private Admin admin;
}
