package com.exam.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "workers")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "worker_id")
    private Long workerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String role;   // cook / cleaner / helper

    @Column(nullable = false)
    private String contact;

    // ✅ Many workers belong to one vendor
    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private MessVendor vendor;
}
