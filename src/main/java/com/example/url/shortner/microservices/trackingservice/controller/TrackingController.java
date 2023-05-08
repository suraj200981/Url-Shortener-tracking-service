package com.example.url.shortner.microservices.trackingservice.controller;


import com.example.url.shortner.microservices.trackingservice.model.ShortenedURL;
import lombok.extern.slf4j.Slf4j;
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


    @GetMapping("/tracking")
    public ResponseEntity<String> trackingUrl(@RequestBody ShortenedURL shortenedURL){
        System.out.println(shortenedURL);

        return  new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
