package com.exam.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exam.dto.AdminDTO;
import com.exam.entity.Admin;
import com.exam.repository.AdminRepository;
import com.exam.service.AdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminDTO addAdmin(AdminDTO dto) {

        // 🔧 EXPLICIT MAPPING (FIX)
        Admin admin = new Admin();
        admin.setName(dto.getName());
        admin.setEmail(dto.getEmail());
        admin.setContact(dto.getContact());
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));

        Admin saved = adminRepository.save(admin);
        return mapper.map(saved, AdminDTO.class);
    }

    @Override
    public AdminDTO getAdminById(Long id) {

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        return mapper.map(admin, AdminDTO.class);
    }

    @Override
    public List<AdminDTO> getAllAdmins() {

        return adminRepository.findAll()
                .stream()
                .map(admin -> mapper.map(admin, AdminDTO.class))
                .collect(Collectors.toList());
    }

   
}
