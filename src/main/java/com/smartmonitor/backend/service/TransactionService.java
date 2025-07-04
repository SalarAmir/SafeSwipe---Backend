package com.smartmonitor.backend.service;

import com.smartmonitor.backend.model.Transaction;
import com.smartmonitor.backend.repository.TransactionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TransactionService {

    private final TransactionRepository repository;
    private final ApplicationEventPublisher eventPublisher;
    private final FraudDetectionService fraudDetectionService;
    
    public TransactionService(TransactionRepository repository, 
                            ApplicationEventPublisher eventPublisher,
                            FraudDetectionService fraudDetectionService) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
        this.fraudDetectionService = fraudDetectionService;
    }

    public Transaction processTransaction(Transaction txn) {
        // Set timestamp if not provided
        if (txn.getTimestamp() == null) {
            txn.setTimestamp(LocalDateTime.now());
        }
        
        // Detect if transaction is suspicious
        boolean isSuspicious = fraudDetectionService.isSuspicious(txn);
        txn.setSuspicious(isSuspicious);
        
        // Save transaction
        Transaction savedTransaction = repository.save(txn);
        
        // Publish event for suspicious transactions
        if (savedTransaction.getSuspicious()) {
            eventPublisher.publishEvent(new SuspiciousTransactionEvent(savedTransaction));
        }
        
        return savedTransaction;
    }

    @Transactional(readOnly = true)
    public List<Transaction> getSuspiciousTransactions() {
        return repository.findBySuspiciousTrue();
    }

    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }
}
