package com.training.basicrabbitmq.senderservice.config.rabbit.properties;

import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Map;

public record RabbitExchangeBindingConfigProp(
    String queueName,
    @DefaultValue("") String routingKey,
    Map<String, String> headers
) {
}
