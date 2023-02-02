package com.example.shop_module.app.mq.kafka;

import com.example.shop_module.app.controller.websocket.WebSocketSender;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaAlgorithmsListener {

    @Autowired
    private WebSocketSender sender;

    @KafkaListener(topics = "algorithms-sort-bubble-answer" , groupId = "messageString")
    public void algorithmsBubbleAnswer (String msg) throws JSONException {
        JSONObject jsonObject = new JSONObject(msg);
        String message = (String) jsonObject.get("message");
        String sessionId = (String) jsonObject.get("sessionId");
        System.out.println(message);
        sender.sendToExample(message, sessionId);
    }

    @KafkaListener(topics = "algorithms-sort-quick-answer" , groupId = "messageString")
    public void algorithmsQuickAnswer (String msg) throws JSONException {
        JSONObject jsonObject = new JSONObject(msg);
        String message = (String) jsonObject.get("message");
        String sessionId = (String) jsonObject.get("sessionId");
        System.out.println(message);
        sender.sendToQuickSort(message, sessionId);
    }
}
