package com.example.shop_module.dto;


import com.example.shop_module.domain.OrdersDetails;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDTO implements Serializable {
    private Long id;
    private String created;
    private String update;
    private String userMail;
    private BigDecimal sum;
    private String address;
    private String status;
    private List<OrdersDetails> orderDetails;
}
