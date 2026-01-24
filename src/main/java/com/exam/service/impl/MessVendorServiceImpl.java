package com.exam.service.impl;

import java.util.*;

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

public class MessVendorServiceImpl implements MessVendorService{

	 private final MessVendorRepository vendorRepository;
	    private final AdminRepository adminRepository;
	    private final ModelMapper modelMapper;

	    @Override
	    public MessVendorDTO saveVendor(MessVendor vendor, Long adminId) {

	        Admin admin = adminRepository.findById(adminId)
	                .orElseThrow(() -> new RuntimeException("Admin not found"));

	        vendor.setAdmin(admin); // 🔗 mapping set
	        MessVendor saved = vendorRepository.save(vendor);

	        return modelMapper.map(saved, MessVendorDTO.class);
	    }

	    @Override
	    public List<MessVendorDTO> getAllVendors() {
	        List<MessVendor> vendors = vendorRepository.findAll();
	        List<MessVendorDTO> list = new ArrayList<>();

	        for (MessVendor v : vendors) {
	            list.add(modelMapper.map(v, MessVendorDTO.class));
	        }
	        return list;
	    }

	    @Override
	    public MessVendorDTO getVendorById(Long id) {
	        MessVendor vendor = vendorRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Vendor not found"));

	        return modelMapper.map(vendor, MessVendorDTO.class);
	    }

	    @Override
	    public void deleteVendor(Long id) {
	        vendorRepository.deleteById(id);
	    }
	    
	    @Override
	    public MessVendorDTO updateVendor(Long id, MessVendor vendor) {

	        MessVendor existing = vendorRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Vendor not found"));

	        existing.setMessName(vendor.getMessName());
	        existing.setEmail(vendor.getEmail());
	        existing.setContact(vendor.getContact());
	        existing.setType(vendor.getType());

	        MessVendor updated = vendorRepository.save(existing);
	        return modelMapper.map(updated, MessVendorDTO.class);
	    }


}
