package com.example.url.shortner.microservices.trackingservice.controller;


import com.example.url.shortner.microservices.trackingservice.model.ShortenedURL;
import com.example.url.shortner.microservices.trackingservice.model.UrlDTO;
import com.example.url.shortner.microservices.trackingservice.repository.TrackingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
public class TrackingController {


    @Autowired
    TrackingRepository repo;


    @GetMapping("/tracking")
    public ResponseEntity<UrlDTO> trackingUrl(@RequestBody ShortenedURL request) {
        System.out.println(request.getShortenedUrl());
        UrlDTO foundUrl = repo.findByShortenedUrl(request.getShortenedUrl());
        if (foundUrl!=null){
            return new ResponseEntity<>(foundUrl, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }
}
