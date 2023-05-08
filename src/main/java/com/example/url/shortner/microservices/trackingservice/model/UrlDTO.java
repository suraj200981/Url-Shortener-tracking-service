package com.example.url.shortner.microservices.trackingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

//used to specify that this object is mapped in a table
@Entity
@Data
//specify the specific table name in the database
@Table(name = "urls")
public class UrlDTO {

    @Id
    private int id;
    private String originalUrl;
    //hide prefix in database
    private String prefix;
    private int clickCount;

    //    private List<String> ipAddress;
    private Date createdAt;
    private String shortenedUrl;

}
