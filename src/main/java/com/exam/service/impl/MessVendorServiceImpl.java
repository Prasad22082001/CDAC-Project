package com.exam.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exam.dto.MessVendorDTO;
import com.exam.entity.Admin;
import com.exam.entity.MessVendor;
import com.exam.repository.AdminRepository;
import com.exam.repository.MessVendorRepository;
import com.exam.service.MessVendorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessVendorServiceImpl implements MessVendorService {

    private final MessVendorRepository vendorRepository;
    private final AdminRepository adminRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MessVendorDTO addVendor(MessVendorDTO dto) {

        // 🔗 Validate admin
        Admin admin = adminRepository.findById(dto.getAdminId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // DTO → Entity
        MessVendor vendor = mapper.map(dto, MessVendor.class);
        vendor.setAdmin(admin);

        // 🔐 IMPORTANT: encode password
        vendor.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Save
        MessVendor saved = vendorRepository.save(vendor);

        // Entity → DTO
        return mapper.map(saved, MessVendorDTO.class);
    }

    @Override
    public MessVendorDTO getVendorById(Long id) {

        MessVendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        return mapper.map(vendor, MessVendorDTO.class);
    }

    @Override
    public List<MessVendorDTO> getAllVendors() {

        return vendorRepository.findAll()
                .stream()
                .map(vendor -> mapper.map(vendor, MessVendorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteVendor(Long id) {

        if (!vendorRepository.existsById(id)) {
            throw new RuntimeException("Vendor not found");
        }

        vendorRepository.deleteById(id);
    }
}
