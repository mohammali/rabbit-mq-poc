package com.training.basicrabbitmq.receiverservice.config.rabbit.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(value = "rabbit")
public record RabbitConfigProp(
    String host,
    List<RabbitQueueConfigProp> queue
) {
}
