package dev.junlog.payment.txn.transaction.event;

import dev.junlog.payment.txn.transaction.dao.Transaction;
import dev.junlog.payment.txn.transaction.dao.TransactionStatus;
import dev.junlog.payment.txn.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TransactionEventConsumer {

    private final TransactionRepository transactionRepository;

    @KafkaListener(topics = "${app.kafka.topics.transactions}", groupId = "payment-consumers")
    @Transactional
    public void handle(TransactionEvent event) {
        Transaction tx = Transaction.builder()
            .userId(event.getUserId())
            .amount(event.getAmount())
            .type(event.getType())
            .status(TransactionStatus.PENDING)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        try {
            Thread.sleep(1000); // 1s delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        boolean success = Math.random() > 0.01; // 99%
        tx.setStatus(success ? TransactionStatus.SUCCESS : TransactionStatus.FAILED);

        transactionRepository.save(tx);
    }
}
