package com.smartmonitor.backend.websocket;

import com.smartmonitor.backend.model.Transaction;
import com.smartmonitor.backend.service.SuspiciousTransactionEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionWebSocketHandler {

    private final SimpMessagingTemplate messagingTemplate;

    public TransactionWebSocketHandler(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleSuspiciousTransaction(SuspiciousTransactionEvent event) {
        Transaction transaction = event.getTransaction();
        messagingTemplate.convertAndSend("/topic/suspicious", transaction);
    }
}
