package com.example.order_module.domain;

import com.example.order_module.dto.OrderDetailsDto;
import com.example.order_module.dto.ProductDTO;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders_details")
public class OrdersDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "title")
    private String title;

    @JoinColumn(name = "product_id")
    private Long product_id;

    public OrdersDetails(ProductDTO productDTO, Long amount) {
        this.order = order;
        this.product_id = productDTO.getId();
        this.amount = new BigDecimal(amount);
        this.price = new BigDecimal(String.valueOf(productDTO.getPrice()));
        this.title = productDTO.getTitle();
    }

    public OrdersDetails(OrderDetailsDto dto){
        this.amount = dto.getAmount();
        this.price = dto.getPrice();

    }
}
