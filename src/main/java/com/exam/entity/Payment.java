package com.exam.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String paymentMode;   // UPI / Cash / Card

    @Column(nullable = false)
    private String status;        // SUCCESS / FAILED

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    // ✅ Many payments done by one student
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
}
