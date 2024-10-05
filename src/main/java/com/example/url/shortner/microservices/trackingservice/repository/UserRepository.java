package com.example.url.shortner.microservices.trackingservice.repository;

import com.example.url.shortner.microservices.trackingservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAddressIgnoreCase(String emailAddress);

}


