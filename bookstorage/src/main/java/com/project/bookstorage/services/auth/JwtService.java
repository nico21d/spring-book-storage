package com.project.bookstorage.services.auth;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;
    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generateToken(UserDetails user, Map<String, Object> extraClaims) {

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiresAt = new Date(issuedAt.getTime() + EXPIRATION_IN_MINUTES * 60 * 1000);

        String jwt = Jwts.builder()
            .claims(extraClaims)
            .subject(user.getUsername())
            .issuedAt(issuedAt)
            .expiration(expiresAt)
            .header().add("typ", "JWT")
            .and()
            .signWith(generateKey(), Jwts.SIG.HS256)
            .compact();

    
        return jwt;
    }

    private SecretKey generateKey() {

        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }

    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parser().verifyWith(generateKey()).build()
            .parseSignedClaims(jwt).getPayload();
    }

}
