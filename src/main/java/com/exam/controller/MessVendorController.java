package com.exam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.exam.dto.MessVendorDTO;
import com.exam.entity.MessVendor;
import com.exam.service.MessVendorService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/vendor")
@CrossOrigin("*")
@AllArgsConstructor
public class MessVendorController {

    private final MessVendorService vendorService;

    // ADD VENDOR (admin ke under)
    @PostMapping("/add/{adminId}")
    public ResponseEntity<MessVendorDTO> addVendor(
            @RequestBody MessVendor vendor,
            @PathVariable Long adminId) {

        return ResponseEntity.ok(vendorService.saveVendor(vendor, adminId));
    }

    // GET ALL
    @GetMapping("/all")
    public ResponseEntity<List<MessVendorDTO>> getAllVendors() {
        return ResponseEntity.ok(vendorService.getAllVendors());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<MessVendorDTO> getVendor(@PathVariable Long id) {
        return ResponseEntity.ok(vendorService.getVendorById(id));
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.ok("Vendor deleted successfully");
    }
    
 // UPDATE VENDOR
    @PutMapping("/update/{id}")
    public ResponseEntity<MessVendorDTO> updateVendor(
            @PathVariable Long id,
            @RequestBody MessVendor vendor) {

        return ResponseEntity.ok(vendorService.updateVendor(id, vendor));
    }

}
