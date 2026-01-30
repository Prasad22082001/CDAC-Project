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
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/vendor")
@CrossOrigin("*")
@AllArgsConstructor
public class MessVendorController {

    private final MessVendorService vendorService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    // 🔐 VENDOR LOGIN (PUBLIC)
    @PostMapping("/login")
    public ResponseEntity<AuthResp> vendorLogin(
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
                new AuthResp(jwt, "Vendor login successful")
        );
    }

    // ➕ ADD VENDOR (ADMIN ONLY)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<MessVendorDTO> addVendor(
            @Valid @RequestBody MessVendorDTO dto) {

        return ResponseEntity.ok(vendorService.addVendor(dto));
    }

    // 👀 GET VENDOR BY ID
    // ADMIN → any vendor
    // VENDOR → only own data
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR')")
    @GetMapping("/{id}")
    public ResponseEntity<MessVendorDTO> getVendor(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal principal) {

        // if logged in user is VENDOR, allow only own record
        if (principal.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_VENDOR"))
                && !principal.getUserId().equals(id)) {

            throw new RuntimeException("Access denied");
        }

        return ResponseEntity.ok(vendorService.getVendorById(id));
    }

    // 👀 GET ALL VENDORS (ADMIN ONLY)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<MessVendorDTO>> getAllVendors() {

        return ResponseEntity.ok(vendorService.getAllVendors());
    }

    // ❌ DELETE VENDOR (ADMIN ONLY)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVendor(@PathVariable Long id) {

        vendorService.deleteVendor(id);
        return ResponseEntity.ok("Vendor deleted successfully");
    }
}
