package dev.junlog.payment.txn.transaction.event;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class TransactionEventProducer {
    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    @Value("${app.kafka.topics.transactions}")
    private String topic;

    public void publish(TransactionEvent event) {
        // round robin
        CompletableFuture<SendResult<String, TransactionEvent>> future = kafkaTemplate.send(topic, event);

        future.whenComplete((res, ex) -> {
            if (ex != null) {
                System.err.println("Kafka publish failed: " + ex.getMessage());
            }
//            else {
//                RecordMetadata md = res.getRecordMetadata();
//                System.out.printf("Kafka published to %s-%d@%d%n", md.topic(), md.partition(), md.offset());
//            }
        });
    }
}