package com.exam.service;

import java.util.List;
import com.exam.dto.AdminDTO;

public interface AdminService {

    AdminDTO addAdmin(AdminDTO dto);

    AdminDTO getAdminById(Long id);

    List<AdminDTO> getAllAdmins();

    void deleteAdmin(Long id);
}
