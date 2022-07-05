package com.training.basicrabbitmq.receiverservice.config.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.training.basicrabbitmq.receiverservice.config.rabbit.properties.RabbitConfigProp;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.nio.charset.StandardCharsets;

@Configuration
@RequiredArgsConstructor
public class RabbitInitializr implements CommandLineRunner {

    private final RabbitConfigProp configProperties;
    private final Connection connection;

    private final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        System.out.println("New message: '" + delivery.getEnvelope().getExchange() + "" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
    };

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < configProperties.queue().size(); i++) {
            Channel channel = connection.createChannel();
            channel.basicConsume(configProperties.queue().get(i).name(), true, deliverCallback, consumerTag -> {
            });
        }
    }

    @PreDestroy
    public void destroy() throws Exception {
        connection.close();
        System.out.println("Callback triggered - @PreDestroy.");
    }
}