package com.exam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.exam.dto.PaymentDTO;
import com.exam.entity.Payment;
import com.exam.service.PaymentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/payment")
@CrossOrigin("*")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // MAKE PAYMENT
    @PostMapping("/pay/{studentId}/{planId}")
    public ResponseEntity<PaymentDTO> makePayment(
            @RequestBody Payment payment,
            @PathVariable Long studentId,
            @PathVariable Long planId) {

        return ResponseEntity.ok(
                paymentService.makePayment(payment, studentId, planId)
        );
    }

    // GET ALL PAYMENTS
    @GetMapping("/all")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    // GET PAYMENT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPayment(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }
}
