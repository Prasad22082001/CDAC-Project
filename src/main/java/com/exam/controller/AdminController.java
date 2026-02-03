package com.exam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.exam.dto.AdminDTO;
import com.exam.dto.AuthRequest;
import com.exam.dto.AuthResp;
import com.exam.security.JwtUtils;
import com.exam.security.UserPrincipal;
import com.exam.service.AdminService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
@AllArgsConstructor
public class AdminController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final AdminService adminService;

    // ================= ADMIN LOGIN =================
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

        return ResponseEntity.ok(
                new AuthResp(token, "Admin login successful")
        );
    }

    // ================= ADD ADMIN (ADMIN ONLY) =================
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<AdminDTO> addAdmin(
            @Valid @RequestBody AdminDTO dto) {

        return ResponseEntity.ok(adminService.addAdmin(dto));
    }

    // ================= GET ALL ADMINS =================
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    // ================= DELETE ADMIN =================
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAdmin(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal principal) {

        // ❌ SELF DELETE NOT ALLOWED
        if (principal.getUserId().equals(id)) {
            throw new RuntimeException("Admin cannot delete own account");
        }

        adminService.deleteAdmin(id);
        return ResponseEntity.ok("Admin deleted successfully");
    }
}
