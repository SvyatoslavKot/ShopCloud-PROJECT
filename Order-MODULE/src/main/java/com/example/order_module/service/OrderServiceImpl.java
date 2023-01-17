package com.example.order_module.service;

import com.example.order_module.domain.*;
import com.example.order_module.dto.OrderDTO;
import com.example.order_module.dto.ProductDTO;
import com.example.order_module.repository.OrderDetailsRepository;
import com.example.order_module.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    @Override
    public void createOrder(List<ProductDTO> productList, String mail) {
        System.out.println(productList);
        Order order = new Order();
        try{
        Map<ProductDTO, Long> productWithAmount = productList.stream()
                .collect(Collectors.groupingBy(productDTO -> productDTO, Collectors.counting()));

        List<OrdersDetails> orderDetails = productWithAmount.entrySet().stream()
                .map(pair -> (new OrdersDetails( pair.getKey(), pair.getValue())))
                .collect(Collectors.toList());

        for (OrdersDetails details : orderDetails) {
            orderDetailsRepository.save(details);
        }

        BigDecimal total = new BigDecimal(orderDetails.stream()
                .map(detail -> detail.getPrice().multiply(detail.getAmount()))
                .mapToDouble(BigDecimal::doubleValue).sum());

        order.setStatus(OrderStatus.NEW);
        order.setSum(total);
        order.setAddress("none");
        order.setOrders_details(orderDetails);
        order.setUser_mail(mail);
        orderRepository.save(order);
        System.out.println(order);
        }catch (Exception e){
    System.out.println(e.getMessage() + " " +  e.getLocalizedMessage());
        }
    }

    @Override
    public List<OrderDTO> getOrdersByMail (String mail) {
        List<Order> orders = orderRepository.findAllByMailNatile(mail);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        if (orders != null ) {
            orderDTOS = orders.stream().map(order -> new OrderDTO(order)).collect(Collectors.toList());
            return orderDTOS;
        }
        return orderDTOS;
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order with id {" + id + "} not found!"));
        return new OrderDTO(order);
    }
}
