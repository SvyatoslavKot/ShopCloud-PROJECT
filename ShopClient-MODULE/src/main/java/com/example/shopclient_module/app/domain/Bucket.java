package com.example.shopclient_module.app.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bucket implements Serializable {
    private Long id;
    private ShopClient shopClient;
   // private List<Product> products;

}
