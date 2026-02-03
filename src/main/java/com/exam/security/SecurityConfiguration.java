package com.exam.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AdminDetailsService adminDetailsService;
    private final VendorDetailsService vendorDetailsService;
    private final StudentDetailsService studentDetailsService;

    // ================= SECURITY FILTER CHAIN =================
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // ✅ CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // ❌ CSRF (JWT based)
            .csrf(csrf -> csrf.disable())

            // ❌ SESSION (STATELESS)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // ================= AUTH RULES =================
            .authorizeHttpRequests(auth -> auth

                // ✅ PREFLIGHT
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // 🔓 PUBLIC (LOGIN + SWAGGER)
                .requestMatchers(
                        "/admin/login",
                        "/student/login",
                        "/vendor/login",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                ).permitAll()

                // ================= ADMIN =================
                .requestMatchers("/admin/**")
                    .hasRole("ADMIN")

                // ================= STUDENT (ADMIN ONLY OPS) =================
                .requestMatchers(
                        "/student/add",
                        "/student/all",
                        "/student/delete/**"
                ).hasRole("ADMIN")

                // ================= VENDOR (ADMIN OPS) =================
                .requestMatchers(
                        "/vendor/add",
                        "/vendor/delete/**"
                ).hasRole("ADMIN")

                // ================= VENDOR LIST =================
                .requestMatchers(HttpMethod.GET, "/vendor/all")
                    .hasAnyRole("ADMIN", "STUDENT")

                // ================= MENU =================
                .requestMatchers(HttpMethod.POST, "/menu/add")
                    .hasAnyRole("ADMIN", "VENDOR")

                .requestMatchers(HttpMethod.DELETE, "/menu/delete/**")
                    .hasAnyRole("ADMIN", "VENDOR")

                .requestMatchers(HttpMethod.GET, "/menu/**")
                    .hasAnyRole("ADMIN", "VENDOR", "STUDENT")

                // ================= WORKER =================
                .requestMatchers("/worker/**")
                    .hasAnyRole("ADMIN", "VENDOR")

                // ================= STUDENT SELF =================
                .requestMatchers(
                        "/student/me",
                        "/student/select-plan/**",
                        "/student/select-vendor/**"
                ).hasRole("STUDENT")

                // ================= ✅ VENDOR → OWN STUDENTS =================
                .requestMatchers("/student/vendor/my-students")
                    .hasRole("VENDOR")

                // ================= PAYMENT =================
                .requestMatchers("/payment/**")
                    .hasAnyRole("ADMIN", "STUDENT")

                .anyRequest().authenticated()
            )

            // 🔑 JWT FILTER
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    // ================= AUTH MANAGER =================
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

    // ================= ADMIN PROVIDER =================
    @Bean
    public DaoAuthenticationProvider adminAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(adminDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // ================= VENDOR PROVIDER =================
    @Bean
    public DaoAuthenticationProvider vendorAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(vendorDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // ================= STUDENT PROVIDER =================
    @Bean
    public DaoAuthenticationProvider studentAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(studentDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // ⚠️ PASSWORD ENCODER (VIVA SAFE)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // ================= CORS =================
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://localhost:8080"
        ));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
