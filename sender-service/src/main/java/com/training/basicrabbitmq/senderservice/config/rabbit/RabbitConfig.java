package com.training.basicrabbitmq.senderservice.config.rabbit;

import com.rabbitmq.client.ConnectionFactory;
import com.training.basicrabbitmq.senderservice.config.rabbit.properties.RabbitConfigProp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitConfig {

    @Bean
    public ConnectionFactory connectionFactory(RabbitConfigProp rabbitConfigProp) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(rabbitConfigProp.host());
        return connectionFactory;
    }

}
