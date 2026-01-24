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
    private Long workerId;

    private String name;
    private String role;     // cook / cleaner / helper
    private String contact;

    // 🔗 mapping: many workers belong to one vendor
    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private MessVendor vendor;
}
