package com.exam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.exam.dto.PaymentDTO;
import com.exam.security.UserPrincipal;
import com.exam.service.PaymentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/payment")
@CrossOrigin("*")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // 💳 STUDENT → MAKE PAYMENT
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/pay")
    public ResponseEntity<PaymentDTO> makePayment(
            @Valid @RequestBody PaymentDTO dto,
            @AuthenticationPrincipal UserPrincipal principal) {

        return ResponseEntity.ok(
                paymentService.makePayment(dto, principal.getUserId())
        );
    }

    // 👀 STUDENT → VIEW OWN PAYMENTS
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/my")
    public ResponseEntity<List<PaymentDTO>> myPayments(
            @AuthenticationPrincipal UserPrincipal principal) {

        return ResponseEntity.ok(
                paymentService.getMyPayments(principal.getUserId())
        );
    }

    // 👑 ADMIN → VIEW ALL PAYMENTS
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {

        return ResponseEntity.ok(paymentService.getAllPayments());
    }
}
