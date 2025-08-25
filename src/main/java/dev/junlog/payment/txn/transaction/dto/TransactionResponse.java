package dev.junlog.payment.txn.transaction.dto;

import dev.junlog.payment.txn.transaction.dao.Transaction;
import dev.junlog.payment.txn.transaction.dao.TransactionStatus;
import dev.junlog.payment.txn.transaction.dao.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponse {
    private Long id;
    private Long userId;
    private BigDecimal amount;
    private TransactionStatus status;
    private TransactionType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TransactionResponse from(Transaction transaction) {
        return TransactionResponse.builder()
            .id(transaction.getId())
            .userId(transaction.getUserId())
            .amount(transaction.getAmount())
            .status(transaction.getStatus())
            .type(transaction.getType())
            .createdAt(transaction.getCreatedAt())
            .updatedAt(transaction.getUpdatedAt())
            .build();
    }
}
