package com.exam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.exam.dto.PaymentDTO;
import com.exam.service.PaymentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/payment")
@CrossOrigin("*")
@AllArgsConstructor
public class PaymentController {

    private PaymentService paymentService;

    // ✅ ADD PAYMENT
    @PostMapping("/add")
    public PaymentDTO addPayment(@RequestBody PaymentDTO dto) {
        return paymentService.addPayment(dto);
    }

    // ✅ GET ALL PAYMENTS
    @GetMapping("/all")
    public List<PaymentDTO> getAllPayments() {
        return paymentService.getAllPayments();
    }

    // ✅ DELETE PAYMENT
    @DeleteMapping("/delete/{id}")
    public String deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return "Payment deleted successfully";
    }
}
