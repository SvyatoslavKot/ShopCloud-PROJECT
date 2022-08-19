package com.example.springintegration.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class OrderDetails {
    private String product;
    private Double price;
    private Double amount;
    private Double sum;

    public OrderDetails() {
    }

    public OrderDetails(String product, Double price, Double amount, Double sum) {
        this.product = product;
        this.price = price;
        this.amount = amount;
        this.sum = sum;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
