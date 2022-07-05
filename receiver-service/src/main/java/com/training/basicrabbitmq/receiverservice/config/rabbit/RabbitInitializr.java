package com.training.basicrabbitmq.receiverservice.config.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.training.basicrabbitmq.receiverservice.config.rabbit.properties.RabbitConfigProp;
import com.training.basicrabbitmq.receiverservice.config.rabbit.properties.RabbitQueueConfigProp;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class RabbitInitializr implements CommandLineRunner {

    private final RabbitConfigProp configProperties;
    private final Connection connection;

    @Override
    public void run(String... args) throws Exception {
        Optional<Channel> maybeChannel = connection.openChannel();
        if (maybeChannel.isPresent()) {
            Channel channel = maybeChannel.get();
            for (RabbitQueueConfigProp config : configProperties.queue()) {
                channel.basicConsume(config.name(), true, new CustomDeliverCallback(config.name()), consumerTag -> {
                });
            }
        }
    }

    @PreDestroy
    public void destroy() throws Exception {
        connection.close();
    }
}