package com.example.springintegration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.sql.DataSourceDefinition;
import java.util.List;

@Data
@Getter

public class Order {
    private Long orderId;
    private String username;
    private String address;
    private List<OrderDetails> details;

    public Order() {
    }

    public Order(Long orderId, String username, String address, List<OrderDetails> details) {
        this.orderId = orderId;
        this.username = username;
        this.address = address;
        this.details = details;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderDetails> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetails> details) {
        this.details = details;
    }
}
