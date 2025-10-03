package dev.junlog.payment.txn.transaction.service;

import dev.junlog.payment.txn.config.ShardRoutingDataSource;
import dev.junlog.payment.txn.transaction.dao.Transaction;
import dev.junlog.payment.txn.transaction.dao.TransactionStatus;
import dev.junlog.payment.txn.transaction.dto.TransactionRequest;
import dev.junlog.payment.txn.transaction.event.TransactionEvent;
import dev.junlog.payment.txn.transaction.event.TransactionEventProducer;
import dev.junlog.payment.txn.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionEventProducer producer;

    public String createTransaction(TransactionRequest request) {
        Transaction tx = Transaction.builder()
            .userId(request.getUserId())
            .amount(request.getAmount())
            .type(request.getType())
            .status(TransactionStatus.PENDING)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        // Todo: implement AOP
        ShardRoutingDataSource.setShardKey(request.getUserId(), 5);
        try {
            transactionRepository.save(tx);
        } finally {
            ShardRoutingDataSource.clear();
        }

        TransactionEvent pending = TransactionEvent.builder()
            .eventId(UUID.randomUUID().toString())
            .userId(request.getUserId())
            .amount(request.getAmount())
            .type(request.getType())
            .status(TransactionStatus.PENDING)
            .createdAt(LocalDateTime.now())
            .build();

        producer.publish(pending);
        return pending.getEventId();
    }

    @Transactional(readOnly = true)
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Transactional
    public Transaction updateStatus(Long id, String status) {
        TransactionStatus newStatus = TransactionStatus.valueOf(status.toUpperCase());
        return transactionRepository.updateStatus(id, newStatus);
    }
}
