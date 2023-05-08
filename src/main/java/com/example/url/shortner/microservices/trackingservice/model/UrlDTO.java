package com.example.url.shortner.microservices.trackingservice.model;

import lombok.Data;

import java.util.Date;

@Data
public class UrlDTO {

    private int id;
    private String originalUrl;
    //hide prefix in database
    private String prefix;
    private int clickCount;

    //    private List<String> ipAddress;
    private Date createdAt;
    private String shortenedUrl;

}
