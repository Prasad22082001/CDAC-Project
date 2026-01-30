package com.exam.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AdminDetailsService adminDetailsService;
    private final VendorDetailsService vendorDetailsService;
    private final StudentDetailsService studentDetailsService;

    // 🔐 SECURITY FILTER CHAIN
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth

                // 🔓 PUBLIC ENDPOINTS
            		.requestMatchers(
            		        "/admin/login",
            		        "/admin/add",   // 👈 ADD THIS
            		        "/student/login",
            		        "/vendor/login",
            		        "/swagger-ui.html",
            		        "/swagger-ui/**",
            		        "/v3/api-docs/**"
            		).permitAll()


                // 🔐 ROLE BASED ACCESS
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/vendor/**").hasRole("VENDOR")
                .requestMatchers("/student/**").authenticated()

                .anyRequest().authenticated()
            )

            // 🔑 JWT FILTER
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    // ✅ AUTHENTICATION MANAGER (ADMIN + VENDOR + STUDENT)
    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(
                List.of(
                        adminAuthProvider(),
                        vendorAuthProvider(),
                        studentAuthProvider()
                )
        );
    }

    // 🔐 ADMIN AUTH PROVIDER
    @Bean
    public DaoAuthenticationProvider adminAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(adminDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // 🔐 VENDOR AUTH PROVIDER
    @Bean
    public DaoAuthenticationProvider vendorAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(vendorDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // 🔐 STUDENT AUTH PROVIDER
    @Bean
    public DaoAuthenticationProvider studentAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(studentDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // ⚠️ TEMP PASSWORD ENCODER (PLAIN TEXT)
    // 👉 Viva line: "Production me BCryptPasswordEncoder use karenge"
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
