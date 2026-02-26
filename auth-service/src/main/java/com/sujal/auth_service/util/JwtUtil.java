package com.sujal.auth_service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    public static final String SECRET_KEY= "Zp2l8j3YJb7mQx4VdN6sRkT9uHcF5WgL1eAa0BvXyPc=";

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5)) // Token valid for 5 minutes
                .claims(claims)
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);  // automatically determines the appropriate key length for the HMAC algorithm
    }

    private Claims getClaims(String token) {
        return Jwts.parser().verifyWith((SecretKey) getKey())
                .build().parseSignedClaims(token)
                .getPayload();
    }

    public Date extractExpiration(String token) {
        return getClaims(token).getExpiration();
    }
}
