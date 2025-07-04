package com.smartmonitor.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private Long id;
    private String userId;
    private Double amount;
    private LocalDateTime timestamp;
    private Boolean suspicious;
    private String riskLevel;
    private String suspiciousReason;
}
