package com.exam.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.time}")
    private long jwtExpirationTime;

    // 🔑 create signing key
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // 🔐 Generate JWT
    public String generateToken(UserPrincipal principal) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationTime);

        return Jwts.builder()
                .setSubject(principal.getUsername())   // email
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("user_id", principal.getUserId())
                .claim("user_role", principal.getUserRole())
                .signWith(getSigningKey())              // ✅ CORRECT
                .compact();
    }

    // 🔎 Validate JWT
    public Claims validateToken(String token) {

        return Jwts.parserBuilder()                     // ✅ CORRECT
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
