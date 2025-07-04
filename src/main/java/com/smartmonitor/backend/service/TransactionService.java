package com.smartmonitor.backend.service;

import com.smartmonitor.backend.model.Transaction;
import com.smartmonitor.backend.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public void processTransaction(Transaction txn) {
        txn.setTimestamp(LocalDateTime.now());
        txn.setSuspicious(txn.getAmount() > 10000);
        repository.save(txn);
    }

    public List<Transaction> getSuspiciousTransactions() {
        return repository.findBySuspiciousTrue();
    }
}
