package com.training.basicrabbitmq.receiverservice.config.rabbit;

import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class CustomDeliverCallback implements DeliverCallback {

    private final String queueName;

    @Override
    public void handle(String consumerTag, Delivery delivery) throws IOException {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        log.info("Exchange: " + delivery.getEnvelope().getExchange() + ", Route Key: " + delivery.getEnvelope().getRoutingKey() + ", Queue: " + queueName + ", Message: " + message + "");
    }
}
