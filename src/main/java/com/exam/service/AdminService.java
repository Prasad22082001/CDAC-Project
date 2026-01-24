package com.exam.service;

import java.util.List;

import com.exam.entity.Admin;

public interface AdminService {
Admin saveAdmin(Admin admin);
List<Admin> getAllAdmins();
Admin getAdminById(Long id);
Admin updateAdmin(Long id, Admin admin);
void deleteAdmin(Long id);
}
