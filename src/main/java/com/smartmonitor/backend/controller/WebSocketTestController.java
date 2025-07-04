package com.smartmonitor.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/websocket")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class WebSocketTestController {

    private final SimpMessagingTemplate messagingTemplate;
    
    public WebSocketTestController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/test")
    public String testWebSocket() {
        Map<String, Object> testMessage = new HashMap<>();
        testMessage.put("id", 999);
        testMessage.put("userId", "testUser");
        testMessage.put("amount", 99999.99);
        testMessage.put("timestamp", LocalDateTime.now());
        testMessage.put("suspicious", true);
        
        System.out.println("🧪 Sending test WebSocket message: " + testMessage);
        messagingTemplate.convertAndSend("/topic/suspicious", testMessage);
        
        return "Test message sent to WebSocket!";
    }

    @GetMapping("/health")
    public Map<String, Object> getWebSocketHealth() {
        Map<String, Object> health = new HashMap<>();
        health.put("timestamp", LocalDateTime.now());
        health.put("websocket_endpoint", "/ws-transactions");
        health.put("topic", "/topic/suspicious");
        health.put("status", "WebSocket service is running");
        
        System.out.println("🏥 WebSocket health check requested");
        return health;
    }

    @GetMapping("/sockjs-info")
    public Map<String, Object> getSockJSInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("endpoint", "/ws-transactions");
        info.put("sockjs_url", "http://localhost:8080/ws-transactions");
        info.put("info_url", "http://localhost:8080/ws-transactions/info");
        info.put("timestamp", LocalDateTime.now());
        info.put("server_status", "WebSocket server is running");
        
        System.out.println("🏥 SockJS info requested");
        return info;
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        System.out.println("🏓 Ping endpoint called from frontend");
        return ResponseEntity.ok("pong - backend is running on port 8080");
    }
    
    @PostMapping("/test-cors")
    public ResponseEntity<Map<String, Object>> testCors(@RequestHeader(value = "Origin", required = false) String origin) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("origin", origin);
        response.put("cors_status", "CORS is working");
        response.put("server_port", "8080");
        
        System.out.println("🌐 CORS test called from origin: " + origin);
        return ResponseEntity.ok(response);
    }
}
