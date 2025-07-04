package com.smartmonitor.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        System.out.println("🔧 Registering WebSocket endpoint: /ws-transactions");
        registry.addEndpoint("/ws-transactions")
                .setAllowedOriginPatterns("*")
                .withSockJS()
                .setStreamBytesLimit(512 * 1024)
                .setHttpMessageCacheSize(1000)
                .setDisconnectDelay(30 * 1000);
        
        // Also add a simple WebSocket endpoint without SockJS for testing
        registry.addEndpoint("/ws-simple")
                .setAllowedOriginPatterns("*");
                
        System.out.println("🔧 WebSocket endpoint registered successfully");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        System.out.println("🔧 Configuring message broker...");
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
        System.out.println("🔧 Message broker configured successfully");
    }
}
