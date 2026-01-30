package com.exam.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.exam.entity.MessVendor;
import com.exam.repository.MessVendorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VendorDetailsService implements UserDetailsService {

    private final MessVendorRepository vendorRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        MessVendor vendor = vendorRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Vendor not found with email: " + email));

        return new UserPrincipal(
                vendor.getVendorId(),          // userId
                vendor.getEmail(),             // username
                vendor.getPassword(),          // ✅ DB PASSWORD (FIXED)
                List.of(new SimpleGrantedAuthority("ROLE_VENDOR")),
                "VENDOR"
        );
    }
}
