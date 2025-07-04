package com.smartmonitor.backend.controller;

import com.smartmonitor.backend.model.Transaction;
import com.smartmonitor.backend.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class TransactionController {

    private final TransactionService service;
    private final SimpMessagingTemplate messagingTemplate;
    
    public TransactionController(TransactionService service, SimpMessagingTemplate messagingTemplate) {
        this.service = service;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        try {
            Transaction processedTransaction = service.processTransaction(transaction);
            
            // Also send via WebSocket directly to ensure real-time updates
            if (processedTransaction.getSuspicious()) {
                System.out.println("🔴 Sending suspicious transaction via WebSocket: " + processedTransaction);
                messagingTemplate.convertAndSend("/topic/suspicious", processedTransaction);
            }
            
            return ResponseEntity.status(HttpStatus.CREATED).body(processedTransaction);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/suspicious")
    public ResponseEntity<List<Transaction>> getSuspiciousTransactions() {
        List<Transaction> suspiciousTransactions = service.getSuspiciousTransactions();
        return ResponseEntity.ok(suspiciousTransactions);
    }
    
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = service.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long id) {
        // This would require a findById method in the service
        return ResponseEntity.notFound().build(); // Placeholder
    }
}
