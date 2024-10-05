package com.example.url.shortner.microservices.trackingservice.controller;

import com.example.url.shortner.microservices.trackingservice.model.ShortenedURL;
import com.example.url.shortner.microservices.trackingservice.model.UrlDTO;
import com.example.url.shortner.microservices.trackingservice.repository.TrackingRepository;
import com.example.url.shortner.microservices.trackingservice.repository.UserRepository;
import com.example.url.shortner.microservices.trackingservice.service.UserService;
import com.example.url.shortner.microservices.trackingservice.model.User;  // Assuming you have a User model to represent the user entity
import com.example.url.shortner.microservices.trackingservice.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@CrossOrigin(origins = "http://localhost:8080/")
@RestController
public class TrackingController {

    @Autowired
    TrackingRepository repo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserService userService; // Service to retrieve user details based on the token (this is a mock for demo purposes)

    // Endpoint to track URL information
    @PostMapping("/tracking")
    public ResponseEntity<?> trackingUrl(@RequestBody ShortenedURL request, @RequestHeader(value = "Authorization", required = false) String token) {
        log.info("Received Authorization header: {}", token);

        // Check if the token is null or empty, to handle guest users
        User user = null;
        if (token != null && !token.isEmpty() && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            try {
                // Extract email from JWT token
                String userEmail = jwtUtil.extractUsername(jwtToken);
                // Find the user by email
                user = userRepository.findByEmailAddressIgnoreCase(userEmail);
            } catch (Exception e) {
                log.error("Invalid token: {}", e.getMessage());
                return new ResponseEntity<>("Invalid or malformed JWT token", HttpStatus.UNAUTHORIZED);
            }
        }

        // Retrieve the shortened URL from the database
        UrlDTO foundUrl = repo.findByShortenedUrl(request.getShortenedUrl());
        if (foundUrl == null) {
            return new ResponseEntity<>("URL not found", HttpStatus.NOT_FOUND);
        }

        // Check if the URL has an associated user and if it matches the current user
        if (foundUrl.getUser() != null && (user == null || !foundUrl.getUser().getEmailAddress().equalsIgnoreCase(user.getEmailAddress()))) {
            return new ResponseEntity<>("You do not have permission to view this URL", HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(foundUrl);
    }


    // NEW: GET Endpoint to return all URLs for a user
    @GetMapping("/user/urls")
    public ResponseEntity<?> getUserUrls(@RequestHeader(value = "Authorization") String token) {
        log.info("Received Authorization header: {}", token);

        // Extract user information from the JWT token
        if (token == null || !token.startsWith("Bearer ")) {
            return new ResponseEntity<>("Authorization header is missing or invalid", HttpStatus.UNAUTHORIZED);
        }

        String jwtToken = token.substring(7);
        String userEmail;
        try {
            userEmail = jwtUtil.extractUsername(jwtToken);
        } catch (Exception e) {
            log.error("Invalid token: {}", e.getMessage());
            return new ResponseEntity<>("Invalid or malformed JWT token", HttpStatus.UNAUTHORIZED);
        }

        // Find the user by email
        User user = userRepository.findByEmailAddressIgnoreCase(userEmail);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        // Retrieve all URLs associated with this user
        List<UrlDTO> userUrls = repo.findAllByUserId(user.getId());
        if (userUrls.isEmpty()) {
            return new ResponseEntity<>("No URLs found for this user", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(userUrls);
    }


}
