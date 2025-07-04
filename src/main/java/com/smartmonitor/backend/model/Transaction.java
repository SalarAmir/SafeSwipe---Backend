package com.smartmonitor.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private double amount;
    private LocalDateTime timestamp;
    private boolean suspicious;

    public void setTimestamp(LocalDateTime now) {
    }

    public void setSuspicious(boolean suspicious) {
        this.suspicious = suspicious;
    }

    public boolean isSuspicious() {
        return suspicious;
    }
    public double getAmount() {
        return this.amount;
    }
}
