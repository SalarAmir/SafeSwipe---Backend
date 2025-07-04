package com.smartmonitor.backend.repository;

import com.smartmonitor.backend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySuspiciousTrue();
    
    List<Transaction> findByUserIdAndTimestampAfter(String userId, LocalDateTime timestamp);
    
    @Query("SELECT t FROM Transaction t WHERE t.userId = :userId ORDER BY t.timestamp DESC")
    List<Transaction> findByUserIdOrderByTimestampDesc(@Param("userId") String userId);
}
