package com.training.basicrabbitmq.senderservice.rabbit;

public record NewMessageDto(
    String exchange,
    String routeKey,
    String message
) {
}
