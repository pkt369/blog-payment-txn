package dev.junlog.payment.txn.transaction.event;

import dev.junlog.payment.txn.transaction.dao.TransactionStatus;
import dev.junlog.payment.txn.transaction.dao.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEvent {
    private Long transactionId;
    private String eventId;
    private Long userId;
    private BigDecimal amount;
    private TransactionType type;
    private TransactionStatus status;
    private LocalDateTime createdAt;
}