package com.exam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.exam.dto.AuthRequest;
import com.exam.dto.AuthResp;
import com.exam.dto.MessVendorDTO;
import com.exam.security.JwtUtils;
import com.exam.security.UserPrincipal;
import com.exam.service.MessVendorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/vendor")
@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
public class MessVendorController {

    private final MessVendorService vendorService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    // 🔐 VENDOR LOGIN
    @PostMapping("/login")
    public ResponseEntity<AuthResp> login(@RequestBody AuthRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        String token = jwtUtils.generateToken(principal);

        return ResponseEntity.ok(new AuthResp(token, "Vendor login successful"));
    }

    // ➕ ADD VENDOR (ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<MessVendorDTO> addVendor(
            @Valid @RequestBody MessVendorDTO dto) {

        return ResponseEntity.ok(vendorService.addVendor(dto));
    }

    // 👀 GET ALL VENDORS (ADMIN + STUDENT)  ✅ FIXED
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("/all")
    public ResponseEntity<List<MessVendorDTO>> getAll() {
        return ResponseEntity.ok(vendorService.getAllVendors());
    }

    // 👀 GET VENDOR (ADMIN / OWN)
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR')")
    @GetMapping("/{id}")
    public ResponseEntity<MessVendorDTO> getOne(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal principal) {

        if (principal.getUserRole().equals("VENDOR")
                && !principal.getUserId().equals(id)) {
            throw new RuntimeException("Access denied");
        }

        return ResponseEntity.ok(vendorService.getVendorById(id));
    }

    // ❌ DELETE (ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.ok("Vendor deleted successfully");
    }
}
