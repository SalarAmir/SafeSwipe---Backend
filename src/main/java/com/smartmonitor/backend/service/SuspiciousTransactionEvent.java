package com.smartmonitor.backend.service;

import com.smartmonitor.backend.model.Transaction;
import org.springframework.context.ApplicationEvent;

public class SuspiciousTransactionEvent extends ApplicationEvent {
    
    private final Transaction transaction;
    
    public SuspiciousTransactionEvent(Transaction transaction) {
        super(transaction);
        this.transaction = transaction;
    }
    
    public Transaction getTransaction() {
        return transaction;
    }
}
