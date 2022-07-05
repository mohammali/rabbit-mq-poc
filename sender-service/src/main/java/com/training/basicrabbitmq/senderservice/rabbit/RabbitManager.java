package com.training.basicrabbitmq.senderservice.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.training.basicrabbitmq.senderservice.config.rabbit.properties.RabbitConfigProp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitManager {

    private final ConnectionFactory connectionFactory;
    private final RabbitConfigProp configProp;

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

    public RabbitConfigProp getConfigProp() {
        return configProp;
    }

}
