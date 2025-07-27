package com.appsdeveloperblog.ws.products;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaConfig {
    public static final String MIN_INSYNC_REPLICAS_NAME = "min.insync.replicas";
    public static final String MIN_INSYNC_REPLICAS_VALUE = "1";

    private final String productTopicName;
    private final int partitionNumber;
    private final int replicaNumber;

    public KafkaConfig(@Value("${product.kafka.topic.name:product-created-events-topic}") String productTopicName, @Value("${product.kafka.partition.number:3}") int partitionNumber, @Value("${product.kafka.replica.number:1}") int replicaNumber) {
        this.productTopicName = productTopicName;
        this.partitionNumber = partitionNumber;
        this.replicaNumber = replicaNumber;
    }

    @Bean
    NewTopic productTopic() {
        return TopicBuilder
                .name(productTopicName)
                .partitions(partitionNumber)
                .replicas(replicaNumber)
                .configs(Map.of(MIN_INSYNC_REPLICAS_NAME, MIN_INSYNC_REPLICAS_VALUE)) //specifies min replicas must be present during writing to topic
                .build();
    }
}
