package com.exam.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exam.entity.Admin;
import com.exam.repository.AdminRepository;
import com.exam.service.AdminService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;



    @Override
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

	@Override
	public List<Admin> getAllAdmins() {
		
		return adminRepository.findAll();
	}

	 @Override
	    public Admin updateAdmin(Long id, Admin admin) {

	        Admin existingAdmin = getAdminById(id);

	        existingAdmin.setName(admin.getName());
	        existingAdmin.setEmail(admin.getEmail());
	        existingAdmin.setContact(admin.getContact());
	        existingAdmin.setPass(admin.getPass());

	        return adminRepository.save(existingAdmin);
	    }

	@Override
	public void deleteAdmin(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Admin getAdminById(Long id) {
		
		return AdminRepository.findById(id);
	}
}
