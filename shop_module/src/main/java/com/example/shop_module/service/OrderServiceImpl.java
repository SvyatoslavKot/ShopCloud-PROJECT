package com.example.shop_module.service;

import com.example.shop_module.config.OrderIntegrationConfig;
import com.example.shop_module.dto.OrderDTO;
import com.example.shop_module.exceptions.NoConnectedToMQException;
import com.example.shop_module.exceptions.ResponseMessageException;
import com.example.shop_module.mq.ProduceOrderModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{

    private final OrderIntegrationConfig integrationConfig;
    private final ProduceOrderModule produceOrderModule;


    @Override
    public ResponseEntity createOrder(OrderDTO orderDTO) {
        try{
            Long orderId = produceOrderModule.createOrder(orderDTO);
            return new ResponseEntity(orderId, HttpStatus.OK);
        }catch (NoConnectedToMQException ncm) {
            log.error(ncm.getMessage(), ncm.getCause());
            return new ResponseEntity(ncm.getMessage(), ncm.getStatus());
        }catch (ResponseMessageException rme) {
            log.error(rme.getMessage(), rme.getCause());
            return new ResponseEntity(rme.getMessage(), rme.getStatus());
        }
    }

    // TODO: 009 09.10.22 create method
    @Override
    public void cancelOrder(Long id) {
        produceOrderModule.cancelOrder(id);
    }

    @Override
    public ResponseEntity getOrderById(Long id) {
        try{
            OrderDTO orderDTO =  produceOrderModule.getOrderById(id);
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
            if (produceOrderModule.orderUpdate(orderDTO)){
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
