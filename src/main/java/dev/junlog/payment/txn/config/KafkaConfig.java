package dev.junlog.payment.txn.config;

import dev.junlog.payment.txn.transaction.event.TransactionEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic transactionsTopic(
        @Value("${app.kafka.topics.transactions}") String topic) {
        return TopicBuilder.name(topic)
            .partitions(1500)
            .replicas(1)
            .build();
    }
}
