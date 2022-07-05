package com.training.basicrabbitmq.senderservice.rabbit;

import com.training.basicrabbitmq.senderservice.config.rabbit.properties.RabbitConfigProp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rabbit")
@RequiredArgsConstructor
public class RabbitController {

    private final RabbitManager manager;

    @GetMapping
    public RabbitConfigProp getQueues() {
        return manager.getConfigProp();
    }

    @PostMapping
    public boolean sendMessage(@RequestBody NewMessageDto body) {
        return manager.sendMessage(body);
    }
}
