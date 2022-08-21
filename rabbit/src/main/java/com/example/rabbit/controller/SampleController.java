package com.example.rabbit.controller;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

    @Autowired
    AmqpTemplate template;

    @Autowired
    RabbitTemplate rabbitTemplate;


    @RequestMapping("/emit")
    @ResponseBody
    String queue1() {
        System.out.println("Emit to queue1");
        template.convertAndSend("queue1","Message to queue-1");
        return "Emit to queue";
    }

    @RequestMapping("/queue")
    @ResponseBody
    String queue2() {
        System.out.println("Emit to queue2");

        for(int i = 0;i<10;i++)
            template.convertAndSend("query-example-2","Message queue2 -> " + i);
        return "Emit to queue2";
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Empty mapping";
    }

    @RequestMapping("/pubSub")
    @ResponseBody
    String pubSub() {
        System.out.println("Emit to exchange-example-3");
        rabbitTemplate.setExchange("exchange-example-3");
        rabbitTemplate.convertAndSend("Publisher message");
        return "Emit to exchange-example-3";
    }



    @RequestMapping("/emit/error")
    @ResponseBody
    String error() {
        System.out.println("Emit as error");
        template.convertAndSend("error", "Error");
        return "Emit as error";
    }

    @RequestMapping("/emit/info")
    @ResponseBody
    String info() {
        System.out.println("Emit as info");
        template.convertAndSend("info", "Info");
        return "Emit as info";
    }

    @RequestMapping("/emit/warning")
    @ResponseBody
    String warning() {
        System.out.println("Emit as warning");
        template.convertAndSend("warning", "Warning");
        return "Emit as warning";
    }


    @RequestMapping("/emit/{key}/{message}")
    @ResponseBody
    String topik(@PathVariable("key") String key, @PathVariable("message") String message) {
        System.out.println(String.format("Emit '%s' to '%s'",message,key));
        rabbitTemplate.setExchange("topic-exchange");
        rabbitTemplate.convertAndSend(key, message);
        return String.format("Emit '%s' to '%s'",message,key);
    }

    @RequestMapping("/process/{message}")
    @ResponseBody
    String error(@PathVariable("message") String message) {
        System.out.println(String.format("Emit '%s'",message));

        String response = (String) rabbitTemplate.convertSendAndReceive("query-remote-producer",message);
        System.out.println(String.format("Received on producer '%s'",response));
        return String.valueOf("returned from worker : " + response);
    }
}
