package com.smartmonitor.backend.service;

import com.smartmonitor.backend.model.Transaction;
import com.smartmonitor.backend.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class FraudDetectionService {

    private static final double HIGH_AMOUNT_THRESHOLD = 10000.0;
    private static final double VELOCITY_THRESHOLD = 5000.0;
    private static final int VELOCITY_TIME_WINDOW_MINUTES = 10;
    
    private final TransactionRepository repository;
    
    public FraudDetectionService(TransactionRepository repository) {
        this.repository = repository;
    }
    
    public boolean isSuspicious(Transaction transaction) {
        return isHighAmount(transaction) || 
               isHighVelocity(transaction) || 
               isUnusualTime(transaction);
    }
    
    private boolean isHighAmount(Transaction transaction) {
        return transaction.getAmount() > HIGH_AMOUNT_THRESHOLD;
    }
    
    private boolean isHighVelocity(Transaction transaction) {
        LocalDateTime timeWindow = LocalDateTime.now().minus(VELOCITY_TIME_WINDOW_MINUTES, ChronoUnit.MINUTES);
        
        List<Transaction> recentTransactions = repository.findByUserIdAndTimestampAfter(
            transaction.getUserId(), timeWindow);
        
        double totalAmount = recentTransactions.stream()
            .mapToDouble(Transaction::getAmount)
            .sum();
            
        return totalAmount > VELOCITY_THRESHOLD;
    }
    
    private boolean isUnusualTime(Transaction transaction) {
        // Consider transactions between 11 PM and 5 AM as potentially suspicious
        int hour = transaction.getTimestamp().getHour();
        return hour >= 23 || hour <= 5;
    }
}
