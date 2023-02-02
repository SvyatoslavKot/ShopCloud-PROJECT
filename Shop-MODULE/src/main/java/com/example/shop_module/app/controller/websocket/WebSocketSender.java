package com.example.shop_module.app.controller.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketSender {

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    public String sendToExample (String output, String sessionId) {
        messagingTemplate.convertAndSend("/topic/example/" + sessionId, output);
        return output;
    }

    public String sendToQuickSort (String output, String sessionId) {
        messagingTemplate.convertAndSend("/topic/sort/quick/" + sessionId, output);
        return output;
    }
}
