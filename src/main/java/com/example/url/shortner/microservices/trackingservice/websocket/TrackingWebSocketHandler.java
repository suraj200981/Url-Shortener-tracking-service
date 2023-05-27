package com.example.url.shortner.microservices.trackingservice.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


@Component
public class TrackingWebSocketHandler implements WebSocketHandler {

    private final Set<WebSocketSession> sessions = new HashSet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Connection established
        sessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // Handle incoming WebSocket message
        String receivedMessage = (String) message.getPayload();
        // Process the received message and send a response if needed
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // Handle WebSocket transport error
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // Connection closed
        sessions.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    // Method to send a message to all connected WebSocket sessions
    public void sendMessageToAllSessions(String message) throws IOException {
        TextMessage textMessage = new TextMessage(message);
        for (WebSocketSession session : sessions) {
            session.sendMessage(textMessage);
        }
    }
}
