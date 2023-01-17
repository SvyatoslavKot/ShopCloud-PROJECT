package com.example.shop_module.app.mq.kafka.producer;

import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class KafkaProducer {

    @Autowired
    @Qualifier("myKafkaTemplate")
    private KafkaTemplate myKafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    public void produce(String topic, Object requestMap)  {
        log.info("Produce message , message{" + requestMap +"}");
        try {
            myKafkaTemplate.send(topic, requestMap).get();

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
