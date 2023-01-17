package com.example.productmodule.app.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties
public class OrderDTO implements Serializable {
    private Long id;
    private String userMail;
    private BigDecimal sum;
    private String address;
    private String status;
    private List<OrderDetailsDto> orderDetails;
}