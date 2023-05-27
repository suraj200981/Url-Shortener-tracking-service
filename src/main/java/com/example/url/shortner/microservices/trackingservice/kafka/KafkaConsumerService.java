package com.example.url.shortner.microservices.trackingservice.kafka;

import com.example.url.shortner.microservices.trackingservice.websocket.TrackingWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class KafkaConsumerService {

    @Autowired
    TrackingWebSocketHandler trackingWebSocketHandler;

    @KafkaListener(topics = "my-topic")
    public void receiveMessage(String message) {
        // Process the consumed message
        System.out.println("Received message: " + message);

        try {
            trackingWebSocketHandler.sendMessageToAllSessions(message);
        } catch (IOException e) {

            System.out.println(e);
            // Handle the exception if sending the WebSocket message fails
        }
    }

}
