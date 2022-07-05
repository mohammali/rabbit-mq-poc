package com.training.basicrabbitmq.senderservice.config.rabbit.properties;

import com.rabbitmq.client.BuiltinExchangeType;

import java.util.List;

public record RabbitExchangeConfigProp(
    String name,
    BuiltinExchangeType type,
    List<RabbitExchangeBindingConfigProp> binding
) {
}
