package com.example.url.shortner.microservices.trackingservice.websocket;

import com.example.url.shortner.microservices.trackingservice.model.UrlDTO;
import com.example.url.shortner.microservices.trackingservice.repository.TrackingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class TrackingWebSocketHandler extends TextWebSocketHandler {


    @Autowired
    TrackingRepository repo;

    @Autowired
    ObjectMapper objectMapper; // Autowire the ObjectMapper bean
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Connection is established
        // You can perform any necessary initialization here
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Connection is closed
        // Clean up any resources or perform necessary actions
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        // Process the received message and perform the tracking logic
        // You can replace the println statement with your desired logic
        System.out.println(payload + " - i got this");

        // Perform the repository logic and prepare the response
        UrlDTO foundUrl = repo.findByShortenedUrl(payload);
        if (foundUrl != null) {

            // Convert the UrlDTO to JSON string
            String responseJson = objectMapper.writeValueAsString(foundUrl);

            // Send the response back to the client
            session.sendMessage(new TextMessage(responseJson));
        } else {
            // Send an error response back to the client
            session.sendMessage(new TextMessage("URL not found"));
        }
    }
}
