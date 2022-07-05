package com.training.basicrabbitmq.senderservice.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.training.basicrabbitmq.senderservice.config.rabbit.properties.RabbitConfigProp;
import com.training.basicrabbitmq.senderservice.config.rabbit.properties.RabbitExchangeBindingConfigProp;
import com.training.basicrabbitmq.senderservice.config.rabbit.properties.RabbitExchangeConfigProp;
import com.training.basicrabbitmq.senderservice.config.rabbit.properties.RabbitQueueConfigProp;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Getter
public class RabbitManager {

    @Getter(AccessLevel.NONE)
    private final ConnectionFactory connectionFactory;
    private final List<String> queue;
    private final List<String> exchange;
    private final List<String> routesKeys;
    private final List<String> headers;

    public RabbitManager(ConnectionFactory connectionFactory, RabbitConfigProp rabbitConfigProp) {
        this.connectionFactory = connectionFactory;
        queue = rabbitConfigProp.queue().stream().map(RabbitQueueConfigProp::name).collect(Collectors.toList());
        exchange = rabbitConfigProp.exchange().stream().map(RabbitExchangeConfigProp::name).collect(Collectors.toList());
        routesKeys = rabbitConfigProp.exchange()
            .stream()
            .flatMap(config -> config.binding().stream())
            .map(RabbitExchangeBindingConfigProp::routingKey)
            .filter(it -> !it.isEmpty())
            .collect(Collectors.toList());
        headers = rabbitConfigProp.exchange()
            .stream()
            .flatMap(config -> config.binding().stream())
            .filter(config -> config.headers() != null)
            .map(config -> config.headers().entrySet())
            .flatMap(Collection::stream)
            .map(entry -> entry.getKey() + " -> " + entry.getValue())
            .collect(Collectors.toList());
    }

    public boolean sendMessage(NewMessageDto dto) {
        try (Connection connection = connectionFactory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.basicPublish(
                dto.exchange(),
                dto.routeKey(),
                MessageProperties.PERSISTENT_TEXT_PLAIN, //keep the message even if the rabbit server restarted
                dto.message().getBytes(StandardCharsets.UTF_8)
            );
            return true;
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }

}
