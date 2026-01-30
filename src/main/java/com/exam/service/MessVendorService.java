package com.exam.service;

import java.util.List;

import com.exam.dto.MessVendorDTO;

public interface MessVendorService {

    MessVendorDTO addVendor(MessVendorDTO dto);

    MessVendorDTO getVendorById(Long id);

    List<MessVendorDTO> getAllVendors();

    void deleteVendor(Long id);
}
