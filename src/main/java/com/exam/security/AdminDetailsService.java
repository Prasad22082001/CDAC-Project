package com.exam.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.exam.entity.Admin;
import com.exam.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Admin not found with email: " + email));

        return new UserPrincipal(
                admin.getAdminId(),          // userId
                admin.getEmail(),            // username
                admin.getPassword(),         // ✅ FIXED HERE
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")),
                "ADMIN"
        );
    }
}
