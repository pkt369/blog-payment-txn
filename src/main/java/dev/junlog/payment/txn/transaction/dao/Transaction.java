package dev.junlog.payment.txn.transaction.dao;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Transaction {
    private Long id;
    private Long userId;
    private BigDecimal amount;
    private TransactionStatus status;
    private TransactionType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
