package com.example.url.shortner.microservices.trackingservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

//used to specify that this object is mapped in a table
@Entity
@Data
//specify the specific table name in the database
@Table(name = "urls")
@ToString(exclude = "user") // Lombok annotation to exclude the 'user' field
public class UrlDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "shortened_url")
    private String shortenedUrl;

    @Column(name = "original_url")
    private String originalUrl;

    @Column(name = "prefix")
    private String prefix;

    @Column(name = "click_count")
    private Integer clickCount = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Define many-to-one relationship with User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference // Prevent infinite recursion
    private User user;

}
