package com.training.basicrabbitmq.senderservice.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("rabbit")
@RequiredArgsConstructor
public class RabbitController {

    private final RabbitManager manager;

    @GetMapping("queue")
    public List<String> getQueues() {
        return manager.getQueue();
    }

    @GetMapping("exchange")
    public List<String> getExchanges() {
        return manager.getExchange();
    }

    @GetMapping("routes")
    public List<String> getRoutes() {
        return manager.getRoutesKeys();
    }

    @GetMapping("headers")
    public List<String> getHeaders() {
        return manager.getHeaders();
    }

    @PostMapping
    public boolean sendMessage(@RequestBody NewMessageDto body) {
        return manager.sendMessage(body);
    }
}
