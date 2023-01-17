package com.example.shop_module.app.service.rabbitMqService;

import com.example.shop_module.app.dto.OrderDTO;
import com.example.shop_module.app.exceptions.NoConnectedToMQException;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.mq.ProduceOrderModule;
import com.example.shop_module.app.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitOrderService implements OrderService {
    @Override
    public ResponseEntity getOrderByMail(String mail) {
        return null;
    }

    private final ProduceOrderModule rabbitProduceOrderModule;

    public RabbitOrderService(@Qualifier("rabbitProduceOrderModule") ProduceOrderModule rabbitProduceOrderModule) {
        this.rabbitProduceOrderModule = rabbitProduceOrderModule;
    }

    @Override
    public ResponseEntity createOrder(OrderDTO orderDTO) {
        try{
            Long orderId = rabbitProduceOrderModule.createOrder(orderDTO);
            return new ResponseEntity(orderId, HttpStatus.OK);
        }catch (NoConnectedToMQException ncm) {
            log.error(ncm.getMessage(), ncm.getCause());
            return new ResponseEntity(ncm.getMessage(), ncm.getStatus());
        }catch (ResponseMessageException rme) {
            log.error(rme.getMessage(), rme.getCause());
            return new ResponseEntity(rme.getMessage(), rme.getStatus());
        }
    }

    @Override
    public void cancelOrder(Long id) {
        rabbitProduceOrderModule.cancelOrder(id);
    }

    @Override
    public ResponseEntity getOrderById(Long id) {
        try{
            OrderDTO orderDTO =  rabbitProduceOrderModule.getOrderById(id);
            return new ResponseEntity(orderDTO,HttpStatus.OK);
        }catch (NoConnectedToMQException nce){
            log.error(nce.getMessage(), nce.getCause());
            return new ResponseEntity(nce.getMessage(), nce.getStatus());
        }catch (ResponseMessageException rme) {
            log.error(rme.getMessage(), rme.getCause());
            return new ResponseEntity(rme.getMessage(), rme.getStatus());
        }catch (Exception e){
            e.getStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Override
    public ResponseEntity updateOrder(OrderDTO orderDTO) {
        try{
            if (rabbitProduceOrderModule.orderUpdate(orderDTO)){
                return new ResponseEntity(orderDTO,HttpStatus.OK);
            }
            return new ResponseEntity("Неудалось обновить данные", HttpStatus.BAD_REQUEST);
        }catch (NoConnectedToMQException nce){
            log.error(nce.getMessage(), nce.getCause());
            return new ResponseEntity(nce.getMessage(), nce.getStatus());
        }catch (ResponseMessageException rme) {
            log.error(rme.getMessage(), rme.getCause());
            return new ResponseEntity(rme.getMessage(), rme.getStatus());
        }
    }
}
