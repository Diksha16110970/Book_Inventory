package com.LoginRegister.example.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    // Generate a secure key
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Generate JWT
//    public String generateToken(String subject) {
//        return Jwts.builder()
//                .setSubject(subject)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token valid for 10 hours
//                .signWith(key, SignatureAlgorithm.HS256) // Use the secure key
//                .compact();
//    }
    
//    public String generateToken(long user_id) {
//        return Jwts.builder()
//        		.setSubject(String.valueOf(user_id)) 
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token valid for 10 hours
//                .signWith(key, SignatureAlgorithm.HS256) // Use the secure key
//                .compact();
//    }
   
    public String generateToken(long user_id) {
        return Jwts.builder()
        		.setSubject(String.valueOf(user_id)) 
//        		.claim("username", name)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token valid for 10 hours
                .signWith(key, SignatureAlgorithm.HS256) // Use the secure key
                .compact();
    }
   

    
    
    

    // Validate JWT
    public boolean isValidToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key) // Use the same key to validate
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false; // Token is invalid
        }
    }

    // Extract subject (e.g., email) from JWT
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // Use the same key
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public Long extractUserId(String token) {
        return Long.valueOf(Jwts.parserBuilder()
                .setSigningKey(key) // Use the same key
                .build()
                .parseClaimsJws(token) // Parse the token
                .getBody()
                .getSubject()); // Extract subject (user ID in this case)
    }
 


    
    
}
