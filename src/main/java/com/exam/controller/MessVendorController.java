package com.exam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.exam.dto.MessVendorDTO;
import com.exam.service.MessVendorService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/vendor")
@CrossOrigin("*")
@AllArgsConstructor
public class MessVendorController {

    private final MessVendorService vendorService;

    // ✅ ADD VENDOR
    @PostMapping("/add")
    public ResponseEntity<MessVendorDTO> addVendor(
            @Valid @RequestBody MessVendorDTO dto) {

        return ResponseEntity.ok(vendorService.addVendor(dto));
    }

    // ✅ GET VENDOR BY ID
    @GetMapping("/{id}")
    public ResponseEntity<MessVendorDTO> getVendor(@PathVariable Long id) {

        return ResponseEntity.ok(vendorService.getVendorById(id));
    }

    // ✅ GET ALL VENDORS
    @GetMapping("/all")
    public ResponseEntity<List<MessVendorDTO>> getAllVendors() {

        return ResponseEntity.ok(vendorService.getAllVendors());
    }
 // ✅ DELETE VENDOR
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVendor(@PathVariable Long id) {

        vendorService.deleteVendor(id);
        return ResponseEntity.ok("Vendor deleted successfully");
    }

}
