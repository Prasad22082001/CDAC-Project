package com.exam.service;

import java.util.List;
import com.exam.dto.MessVendorDTO;
import com.exam.entity.MessVendor;

public interface MessVendorService {

    MessVendorDTO saveVendor(MessVendor vendor, Long adminId);

    List<MessVendorDTO> getAllVendors();

    MessVendorDTO getVendorById(Long id);

    void deleteVendor(Long id);
    
    MessVendorDTO updateVendor(Long id, MessVendor vendor);

}
