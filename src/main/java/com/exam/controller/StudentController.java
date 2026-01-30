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

    // 🔐 STUDENT LOGIN (PUBLIC)
    @PostMapping("/login")
    public ResponseEntity<AuthResp> studentLogin(
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
                new AuthResp(jwt, "Student login successful")
        );
    }

    // 👑 ADMIN → ALL STUDENTS
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // 👑 ADMIN → ADD STUDENT
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<StudentDTO> addStudent(
            @Valid @RequestBody StudentDTO dto) {

        return ResponseEntity.ok(studentService.addStudent(dto));
    }

    // 👀 ADMIN → any student
    // 👤 STUDENT → own profile only
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudent(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal principal) {

        if (principal.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"))
                && !principal.getUserId().equals(id)) {

            throw new RuntimeException("Access denied");
        }

        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // 🎓 STUDENT → SELECT PLAN
    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/select-plan/{planId}")
    public ResponseEntity<StudentDTO> selectPlan(
            @PathVariable Long planId,
            @AuthenticationPrincipal UserPrincipal principal) {

        return ResponseEntity.ok(
                studentService.selectPlan(principal.getUserId(), planId)
        );
    }

    // 👑 ADMIN → DELETE STUDENT
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {

        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }
}
