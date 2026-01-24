package com.exam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.exam.dto.AdminDTO;
import com.exam.entity.Admin;
import com.exam.service.AdminService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // ADD ADMIN
    @PostMapping("/add")
    public ResponseEntity<AdminDTO> addAdmin(@RequestBody Admin admin) {
        return ResponseEntity.ok(adminService.saveAdmin(admin));
    }

    // GET ALL ADMINS
    @GetMapping("/all")
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    // GET ADMIN BY ID
    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getAdminById(id));
    }

    // UPDATE ADMIN
    @PutMapping("/update/{id}")
    public ResponseEntity<AdminDTO> updateAdmin(
            @PathVariable Long id,
            @RequestBody Admin admin) {

        return ResponseEntity.ok(adminService.updateAdmin(id, admin));
    }

    // DELETE ADMIN
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok("Admin deleted successfully");
    }
}
