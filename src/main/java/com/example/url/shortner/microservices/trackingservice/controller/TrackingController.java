package com.example.url.shortner.microservices.trackingservice.controller;


import com.example.url.shortner.microservices.trackingservice.model.ShortenedURL;
import com.example.url.shortner.microservices.trackingservice.model.UrlDTO;
import com.example.url.shortner.microservices.trackingservice.repository.TrackingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
public class TrackingController {
    @Autowired
    TrackingRepository repo;

    @MessageMapping("/tracking")
    @SendTo("/topic/tracking-url")
    public UrlDTO trackingUrl(@RequestBody ShortenedURL request) {
        System.out.println(request.getShortenedUrl());
        UrlDTO foundUrl = repo.findByShortenedUrl(request.getShortenedUrl());
        if (foundUrl!=null){
            return foundUrl;
        }else{
            log.error("Error in finding URL object from database");
            return null;
        }
    }
}
