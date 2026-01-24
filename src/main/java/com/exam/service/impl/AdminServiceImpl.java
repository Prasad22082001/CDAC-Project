package com.exam.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.AdminDTO;
import com.exam.entity.Admin;
import com.exam.repository.AdminRepository;
import com.exam.service.AdminService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;

    @Override
    public AdminDTO saveAdmin(Admin admin) {
        Admin saved = adminRepository.save(admin);
        return modelMapper.map(saved, AdminDTO.class);
    }

    @Override
    public List<AdminDTO> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream()
                     .map(a -> modelMapper.map(a, AdminDTO.class))
                     .toList();
    }

    @Override
    public AdminDTO getAdminById(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        return modelMapper.map(admin, AdminDTO.class);
    }

    @Override
    public AdminDTO updateAdmin(Long id, Admin admin) {
        Admin existing = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        existing.setName(admin.getName());
        existing.setEmail(admin.getEmail());
        existing.setContact(admin.getContact());
        existing.setPass(admin.getPass());

        Admin updated = adminRepository.save(existing);
        return modelMapper.map(updated, AdminDTO.class);
    }

    @Override
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
}


/*
 * package com.exam.service.impl;
 * 
 * import java.util.List;
 * 
 * import org.springframework.stereotype.Service;
 * 
 * import com.exam.entity.Admin; import com.exam.repository.AdminRepository;
 * import com.exam.service.AdminService;
 * 
 * import lombok.AllArgsConstructor;
 * 
 * 
 * @AllArgsConstructor
 * 
 * @Service public class AdminServiceImpl implements AdminService {
 * 
 * private final AdminRepository adminRepository;
 * 
 * 
 * 
 * @Override public Admin saveAdmin(Admin admin) { return
 * adminRepository.save(admin); }
 * 
 * @Override public List<Admin> getAllAdmins() {
 * 
 * return adminRepository.findAll(); }
 * 
 * @Override public Admin updateAdmin(Long id, Admin admin) {
 * 
 * Admin existingAdmin = getAdminById(id);
 * 
 * existingAdmin.setName(admin.getName());
 * existingAdmin.setEmail(admin.getEmail());
 * existingAdmin.setContact(admin.getContact());
 * existingAdmin.setPass(admin.getPass());
 * 
 * return adminRepository.save(existingAdmin); }
 * 
 * @Override public void deleteAdmin(Long id) { adminRepository.deleteById(id);
 * }
 * 
 * 
 * @Override public Admin getAdminById(Long id) { return
 * adminRepository.findById(id) .orElseThrow(() -> new
 * RuntimeException("Admin not found with id: " + id)); }
 * 
 * }
 */
