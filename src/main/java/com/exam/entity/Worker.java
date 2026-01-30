package com.exam.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "workers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String role;   // cook / cleaner / helper

    @Column(nullable = false)
    private String contact;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private MessVendor vendor;
}
