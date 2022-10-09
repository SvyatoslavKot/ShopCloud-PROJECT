package com.example.order_module.dto;

import com.example.order_module.domain.Order;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@ToString
public class OrderDTO implements Serializable {
    private Long id;
    private String userMail;
    private BigDecimal sum;
    private String address;
    private String phone;
    private String delivery;
    private String status;
    private List<OrderDetailsDto> orderDetails;


}
