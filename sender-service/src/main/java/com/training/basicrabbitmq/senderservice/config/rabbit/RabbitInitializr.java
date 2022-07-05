package com.training.basicrabbitmq.senderservice.config.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.training.basicrabbitmq.senderservice.config.rabbit.properties.RabbitConfigProp;
import com.training.basicrabbitmq.senderservice.config.rabbit.properties.RabbitExchangeBindingConfigProp;
import com.training.basicrabbitmq.senderservice.config.rabbit.properties.RabbitExchangeConfigProp;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RabbitInitializr implements CommandLineRunner {

    private final ConnectionFactory connectionFactory;
    private final RabbitConfigProp rabbitConfig;

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = connectionFactory.newConnection()) {
            Channel channel = connection.createChannel();
            initExchanges(channel, rabbitConfig.exchange());
        }
    }

    private void initExchanges(Channel channel, List<RabbitExchangeConfigProp> list) throws IOException {
        for (RabbitExchangeConfigProp config : list) {
            channel.exchangeDeclare(config.name(), config.type(), true);
            for (RabbitExchangeBindingConfigProp bindingConfig : config.binding()) {
                channel.queueDeclare(bindingConfig.queueName(), true, false, false, null);
                channel.queueBind(
                    bindingConfig.queueName(),
                    config.name(),
                    bindingConfig.routingKey(),
                    bindingConfig.headers() == null ? null : new HashMap<>(bindingConfig.headers())
                );
            }
        }
    }
}
