package com.smartmonitor.backend.controller;

import com.smartmonitor.backend.model.Transaction;
import com.smartmonitor.backend.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> addTransaction(@RequestBody Transaction txn) {
        service.processTransaction(txn);
        return ResponseEntity.ok("Transaction processed");
    }

    @GetMapping("/suspicious")
    public List<Transaction> getSuspicious() {
        return service.getSuspiciousTransactions();
    }
}
