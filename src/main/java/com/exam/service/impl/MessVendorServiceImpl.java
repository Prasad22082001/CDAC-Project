package com.exam.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.MessVendorDTO;
import com.exam.entity.Admin;
import com.exam.entity.MessVendor;
import com.exam.repository.AdminRepository;
import com.exam.repository.MessVendorRepository;
import com.exam.service.MessVendorService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessVendorServiceImpl implements MessVendorService {

    private final MessVendorRepository vendorRepository;
    private final AdminRepository adminRepository;
    private final ModelMapper mapper;

    @Override
    public MessVendorDTO addVendor(MessVendorDTO dto) {

        // 🔗 resolve adminId
        Admin admin = adminRepository.findById(dto.getAdminId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // DTO → Entity
        MessVendor vendor = mapper.map(dto, MessVendor.class);
        vendor.setAdmin(admin);

        // save
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
                .map(v -> mapper.map(v, MessVendorDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public void deleteVendor(Long id) {

        MessVendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        vendorRepository.delete(vendor);
    }

}
