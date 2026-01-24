package com.exam.service;

import java.util.List;

import com.exam.dto.AdminDTO;   // ✅ ADD THIS
import com.exam.entity.Admin;

public interface AdminService {

    AdminDTO saveAdmin(Admin admin);

    List<AdminDTO> getAllAdmins();

    AdminDTO getAdminById(Long id);

    AdminDTO updateAdmin(Long id, Admin admin);

    void deleteAdmin(Long id);
}
