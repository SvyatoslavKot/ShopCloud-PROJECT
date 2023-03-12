package com.example.shop_module.app.mq.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.ExecutionException;

@Slf4j
public class KafkaProducer extends KafkaAbstractProducer{

    public KafkaProducer(KafkaTemplate kafkaTemplate) {
        super(kafkaTemplate);
    }

    public void produce(String topic, Object message)  {
        log.info("Produce message , message{" + message +"}");
        try {
            kafkaTemplate.send(topic, message).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
/*
    public String produceStringReplyUser(String requestTopic,String replyTopic, String request) throws InterruptedException {
        final String[] r = {null};
        final String  [] response = new String[]{""};
        String o = null;
        ProducerRecord<String, String > record = new ProducerRecord<String, String>(requestTopic, request);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, replyTopic.getBytes()));
        RequestReplyFuture<String, String, UserDTO> sendAndReceive = kafkaTemplate.sendAndReceive(record);
        sendAndReceive.addCallback(new ListenableFutureCallback<ConsumerRecord<String, UserDTO>>() {
            @Override
            public void onFailure(Throwable ex) {
                throw new ResponseMessageException(HttpStatus.NO_CONTENT,"no response from service");
            }

            @Override
            public void onSuccess(ConsumerRecord<String, UserDTO> result) {
                UserDTO u = result.value();
                System.out.println(result.toString());
            }
        });

        System.out.println("response -> " + r[0]);

        return null;


    }

 */




}
