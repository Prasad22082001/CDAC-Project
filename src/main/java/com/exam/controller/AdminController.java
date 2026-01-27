package com.exam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    // 🔐 ADMIN LOGIN (JWT)
    @PostMapping("/login")
    public ResponseEntity<AuthResp> adminLogin(
            @RequestBody AuthRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        UserPrincipal principal =
                (UserPrincipal) authentication.getPrincipal();

        String jwt = jwtUtils.generateToken(principal);

        return ResponseEntity.ok(
                new AuthResp(jwt, "Admin login successful")
        );
    }

    // ✅ ADD ADMIN (JWT PROTECTED)
    @PostMapping("/add")
    public ResponseEntity<AdminDTO> addAdmin(
            @Valid @RequestBody AdminDTO dto) {

        return ResponseEntity.ok(adminService.addAdmin(dto));
    }

    // ✅ GET ADMIN BY ID (JWT PROTECTED)
    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdmin(@PathVariable Long id) {

        return ResponseEntity.ok(adminService.getAdminById(id));
    }

    // ✅ GET ALL ADMINS (JWT PROTECTED)
    @GetMapping("/all")
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {

        return ResponseEntity.ok(adminService.getAllAdmins());
    }
}
