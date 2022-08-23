package com.example.rabbit.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@EnableRabbit
@Component
public class RabbitMqListener {

    Random random = new Random();

    @RabbitListener(queues = "queue1")
    public void processQueue1(String message) {
        System.out.println("Received from queue 1: " + message);
    }

    @RabbitListener(queues = "query-example-2")
    public void worker2(String message) throws InterruptedException {
        System.out.println("worker 1 : " + message);
        Thread.sleep(100 * random.nextInt(20));
    }

    @RabbitListener(queues = "query-example-2")
    public void worker3(String message) throws InterruptedException {
        System.out.println("worker 2 : " + message);
        Thread.sleep(100 * random.nextInt(20));
    }

    //---Publish/Subscribe--
    @RabbitListener(queues = "query-example-3-1")
    public void worker4(String message) {
        System.out.println("accepted on subscribe 1 : " + message);
    }

    @RabbitListener(queues = "query-example-3-2")
    public void worker5(String message) {
        System.out.println("accepted on subscribe 2 : " + message);
    }
    //-------------------//-------------------//-----------------

    //---Publish/Subscribe--

    @RabbitListener(queues = "routing-queue-1")
    public void worker6(String message) {
        System.out.println("accepted on worker routing-queue-1 : " + message);
    }

    @RabbitListener(queues = "routing-queue-2")
    public void worker7(String message) {
        System.out.println("accepted on worker routing-queue-2 : " + message);
    }
    //-------------------//-------------------//-----------------

    //---Topic--

    @RabbitListener(queues = "topic-queue-1")
    public void worker8(String message) {
        System.out.println("accepted on worker topic-queue-1 : " + " message queue -> " +  message);
    }

    @RabbitListener(queues = "topic-queue-2")
    public void worker9(String message) {
        System.out.println("accepted on worker topic-queue-2 : " + " message queue -> "  + message);
    }
    //-------------------//-------------------//-----------------

    //---Remote procedure call (RPC)--

    @RabbitListener(queues = "query-remote-producer")
    public String worker10(String message) throws InterruptedException {
        System.out.println("Received on worker : " + message);
       // Thread.sleep(3000);
        return "Query-Remote message from addressLine : " + message;
    }
}
