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
    private Long paymentId;

    private double amount;
    private String paymentMode;   // UPI / Cash / Card
    private String status;        // SUCCESS / FAILED
    private LocalDateTime paymentDate;

    // 🔗 many payments -> one student
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // 🔗 many payments -> one plan
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private MessPlan plan;
}
