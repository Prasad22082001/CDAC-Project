package com.exam.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mess_vendors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessVendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorId;

    private String messName;
    private String email;
    private String password;
    private String contact;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @OneToMany(mappedBy = "vendor")
    private List<Menu> menus;

    @OneToMany(mappedBy = "vendor")
    private List<Worker> workers;
}
