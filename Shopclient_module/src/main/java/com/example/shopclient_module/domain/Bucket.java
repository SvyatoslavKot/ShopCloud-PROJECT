package com.example.shopclient_module.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bucket implements Serializable {
    private Long id;
    private ShopClient shopClient;
   // private List<Product> products;

}
