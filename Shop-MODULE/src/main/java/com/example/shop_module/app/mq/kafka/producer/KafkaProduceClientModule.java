package com.example.shop_module.app.mq.kafka.producer;

import com.example.shop_module.app.domain.enums.Role;
import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.mq.ProduceShopClient;
import com.example.shop_module.app.mq.kafka.KafkaSettings;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaProduceClientModule implements ProduceShopClient {

    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;

    @Override
    public UserDTO newClientMsg(UserDTO user) {
        return null;
    }

    @Override
    public UserDTO getClientByMail(String mail) {
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
    public void updateClient(UserDTO userDTO) {
            try{
                kafkaProducer.produce(KafkaSettings.TOPIC_CLIENT_MODULE_UPDATE_CLIENT.getValue(),userDTO);
                kafkaProducer.produce(KafkaSettings.TOPIC_CLIENT_MODULE_GET_CLIENT_BY_MAIL.getValue(), userDTO.getMail());
            }catch (Exception e) {
                throw new ResponseMessageException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
    }

    @Override
    public List<UserDTO> findAll() {
        return null;
    }
}
