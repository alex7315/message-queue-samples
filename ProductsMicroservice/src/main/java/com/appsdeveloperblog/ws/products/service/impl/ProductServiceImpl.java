package com.appsdeveloperblog.ws.products.service.impl;

import com.appsdeveloperblog.ws.products.model.CreateProductRestModel;
import com.appsdeveloperblog.ws.products.service.ProductCreatedEvent;
import com.appsdeveloperblog.ws.products.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private final String topicName;

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate, @Value("${product.kafka.topic.name:product-created-events-topic}") String productTopicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = productTopicName;
    }

    @Override
    public String createProduct(CreateProductRestModel product) {
        var productId = UUID.randomUUID().toString();
        //TODO: product details have to persisted before publishing an Event

        var productCreateEvent = ProductCreatedEvent.builder()
                .productId(productId)
                .title(product.getTitle())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();

        // to send message synchronously - makes sure that product is created
//        kafkaTemplate.send(topicName, productId, productCreateEvent);

        //to send message async way
        CompletableFuture<SendResult<String, ProductCreatedEvent>> future =kafkaTemplate.send(topicName, productId, productCreateEvent);
        future.whenComplete((result, ex) -> {
           if(ex == null) {
                log.info("***** Successfully send message: {}", result.getRecordMetadata());
           } else {
               log.error("***** Failed to send message: {}", ex.getMessage());
           }
        });

//        future.join(); //blocks thread to wait for response

        log.info("*********** Return product id *****************");
        return productId;
    }
}
