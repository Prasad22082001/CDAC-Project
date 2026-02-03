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
    public UserDetails loadUserByUsername(String email) {

        MessVendor vendor = vendorRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Vendor not found"));

        return new UserPrincipal(
                vendor.getVendorId(),
                vendor.getEmail(),
                vendor.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_VENDOR")),
                "VENDOR"
        );
    }
}
