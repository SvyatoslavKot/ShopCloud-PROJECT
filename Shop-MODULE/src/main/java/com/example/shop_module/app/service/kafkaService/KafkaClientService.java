package com.example.shop_module.app.service.kafkaService;

import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.mq.kafka.KafkaSettings;
import com.example.shop_module.app.service.abstraction.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaClientService extends KafkaAbstractService implements UserService {

    public KafkaClientService(KafkaTemplate kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    public ResponseEntity registrationClient(UserDTO userDTO) throws ResponseMessageException {
        return null;
    }

    @Override
    public ResponseEntity findByMail(String mail) {
        /* String response = null;
        try {
            response = kafkaProducer.produceStringReplyUser(
                    KafkaSettings.TOPIC_CLIENT_MODULE_MESSAGE_REQUEST.getValue(),
                    KafkaSettings.TOPIC_CLIENT_MODULE_MESSAGE_REPLY.getValue(),
                    mail);
        } catch (InterruptedException e) {
            throw  new ResponseMessageException(HttpStatus.CONFLICT, e.getMessage());
        }

        try {
            JSONObject jsonObject = new JSONObject(response);
            log.info("json {}", jsonObject);
            UserDTO userDTO = UserDTO.builder()
                    .firstName(jsonObject.getString("firstName"))
                    .lastName(jsonObject.getString("lastName"))
                    .mail(jsonObject.getString("mail"))
                    .role(Role.valueOf(jsonObject.getString("role").toUpperCase(Locale.ROOT)))
                    .address(jsonObject.getString("address"))
                    .bucket_id(jsonObject.getLong("bucketId"))
                    .build();
            return userDTO;



        } catch (JSONException e) {
            throw  new ResponseMessageException(HttpStatus.CONFLICT, "can't parse message response!");
        }
*/
        return null;
    }

    @Override
    public ResponseEntity findAllUserDto() {
        return null;
    }

    @Override
    public void updateProfile(UserDTO userDTO) {
        try{
            producer.produce(KafkaSettings.TOPIC_CLIENT_MODULE_UPDATE_CLIENT.getValue(),userDTO);
        }catch (Exception e) {
            throw new ResponseMessageException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
