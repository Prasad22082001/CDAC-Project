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
import com.exam.dto.StudentDTO;
import com.exam.security.JwtUtils;
import com.exam.security.UserPrincipal;
import com.exam.service.StudentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/student")
@CrossOrigin("*")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<AuthResp> studentLogin(@RequestBody AuthRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(principal);

        return ResponseEntity.ok(new AuthResp(jwt, "Student login successful"));
    }

    // ================= ADMIN → ADD STUDENT ✅🔥 =================
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<StudentDTO> addStudent(
            @Valid @RequestBody StudentDTO dto) {

        return ResponseEntity.ok(studentService.addStudent(dto));
    }

    // ================= ADMIN → ALL STUDENTS =================
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // ================= STUDENT → OWN PROFILE =================
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/me")
    public ResponseEntity<StudentDTO> getMyProfile(
            @AuthenticationPrincipal UserPrincipal principal) {

        return ResponseEntity.ok(
                studentService.getStudentById(principal.getUserId())
        );
    }

    // ================= STUDENT → SELECT PLAN =================
    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/select-plan/{planId}")
    public ResponseEntity<StudentDTO> selectPlan(
            @PathVariable Long planId,
            @AuthenticationPrincipal UserPrincipal principal) {

        return ResponseEntity.ok(
                studentService.selectPlan(principal.getUserId(), planId)
        );
    }

    // ================= STUDENT → SELECT VENDOR =================
    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/select-vendor/{vendorId}")
    public ResponseEntity<StudentDTO> selectVendor(
            @PathVariable Long vendorId,
            @AuthenticationPrincipal UserPrincipal principal) {

        return ResponseEntity.ok(
                studentService.selectVendor(principal.getUserId(), vendorId)
        );
    }
 // ================= ADMIN → DELETE STUDENT ❌ =================
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {

        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }
    
 // ================= VENDOR → VIEW OWN STUDENTS =================
    @PreAuthorize("hasRole('VENDOR')")
    @GetMapping("/vendor/my-students")
    public ResponseEntity<List<StudentDTO>> getMyStudents(
            @AuthenticationPrincipal UserPrincipal principal) {

        return ResponseEntity.ok(
                studentService.getStudentsByVendor(principal.getUserId())
        );
    }


}
