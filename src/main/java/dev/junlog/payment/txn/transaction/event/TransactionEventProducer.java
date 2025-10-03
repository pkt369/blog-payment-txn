package dev.junlog.payment.txn.transaction.event;

import dev.junlog.payment.txn.transaction.dao.Transaction;
import dev.junlog.payment.txn.transaction.dao.TransactionStatus;
import dev.junlog.payment.txn.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class TransactionEventProducer {
    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;
    private final TransactionRepository transactionRepository;

    @Value("${app.kafka.topics.transactions}")
    private String topic;

    @Transactional
    public void publish(TransactionEvent event) {
        event.setTransactionId(event.getTransactionId());

        // round robin
        CompletableFuture<SendResult<String, TransactionEvent>> future = kafkaTemplate.send(topic, event);

        future.whenComplete((res, ex) -> {
            if (ex != null) {
                System.err.println("Kafka publish failed: " + ex.getMessage());
            }
        });
    }
}