package com.exam.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false)
    private double amount;

    // UPI / CASH / CARD
    @Column(nullable = false)
    private String paymentMode;

    // PENDING / SUCCESS / FAILED
    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    // many payments → one student
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
}
