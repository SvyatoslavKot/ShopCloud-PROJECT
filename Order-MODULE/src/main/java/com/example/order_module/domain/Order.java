package com.example.order_module.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
@ToString
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    //@CreationTimestamp
  //  private Date created;

   // @UpdateTimestamp
   // private Date update;

    @Column(name = "sum")
    private BigDecimal sum;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery")
    private DeliveryType deliveryType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "phone")
    private String phone;

    @Column(name = "user_mail")
    private String user_mail;

    @OneToMany
    private List<OrdersDetails> orders_details;


}
