package com.example.shop_module.app.controller.websocket;

import com.example.shop_module.app.domain.MessageFromSocket;
import com.example.shop_module.app.service.AlgorithmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketMessageController {
    @Autowired
    AlgorithmsService algorithmsService;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/example")
    public void example (MessageFromSocket message) {
        System.out.println(message.getMessage());
        System.out.println(message.getSessionId());
        algorithmsService.sortBubble(message);
    }

    @MessageMapping("/sort/quick")
    public void sortQuick (MessageFromSocket message) {
        System.out.println(message.getMessage());
        System.out.println(message.getSessionId());
        algorithmsService.sortQuick(message);
    }


}
