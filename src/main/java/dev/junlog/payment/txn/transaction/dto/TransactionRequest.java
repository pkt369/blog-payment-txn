package dev.junlog.payment.txn.transaction.dto;

import dev.junlog.payment.txn.transaction.dao.TransactionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private Long userId;
    private BigDecimal amount;
    private TransactionType type;
}
