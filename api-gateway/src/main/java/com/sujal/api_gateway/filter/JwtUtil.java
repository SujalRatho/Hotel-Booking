package com.sujal.api_gateway.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;

@Component
public class JwtUtil {

    public static final String SECRET_KEY= "Zp2l8j3YJb7mQx4VdN6sRkT9uHcF5WgL1eAa0BvXyPc=";


    public void validateToken(String token){
        Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token);
    }


    private Key getKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
