package com.example.url.shortner.microservices.trackingservice.repository;

import com.example.url.shortner.microservices.trackingservice.model.UrlDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackingRepository extends JpaRepository<UrlDTO, Integer> {

    UrlDTO findByShortenedUrl(String shortenedUrl);

    List<UrlDTO> findAllByUserId(Long Id);

}
