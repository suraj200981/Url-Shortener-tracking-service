package com.example.url.shortner.microservices.trackingservice.service;

import com.example.url.shortner.microservices.trackingservice.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class UserService {

    // Replace with your JWT secret key (ensure it's securely stored, not hard-coded in production)
    private static final String SECRET_KEY = "secret";

    // Method to get user details from a token (actual implementation)
    public User getUserFromToken(String token) {
        try {
            // Decode the JWT token
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Extract user information from claims
            Long userId = Long.parseLong(claims.get("userId").toString());
            String email = claims.get("email").toString();

            // Create and return a user object
            User user = new User();
            user.setId(userId);
            user.setEmailAddress(email);
            return user;

        } catch (Exception e) {
            // Log the exception (you can replace with a logger)
            System.err.println("Invalid token: " + e.getMessage());
            return null; // Return null if the token is invalid
        }
    }

    // Mock method to generate a JWT token (for testing purposes)
    public String generateToken(User user) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return Jwts.builder()
                .claim("userId", user.getId())
                .claim("email", user.getEmailAddress())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
