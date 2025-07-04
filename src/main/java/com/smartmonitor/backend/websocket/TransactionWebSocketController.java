package com.smartmonitor.backend.websocket;

import com.smartmonitor.backend.model.Transaction;
import com.smartmonitor.backend.service.SuspiciousTransactionEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public TransactionWebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleSuspiciousTransaction(SuspiciousTransactionEvent event) {
        Transaction transaction = event.getTransaction();
        System.out.println("📡 WebSocket Event Handler: Received suspicious transaction event for: " + transaction);
        
        // Send to both endpoints for compatibility
        messagingTemplate.convertAndSend("/topic/suspicious", transaction);
        System.out.println("📡 WebSocket Event Handler: Sent transaction to /topic/suspicious");
    }
}
