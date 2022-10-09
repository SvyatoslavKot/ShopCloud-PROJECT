package com.example.productmodule.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private int amountInStock;
    private String title;
    private BigDecimal price;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name ="products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    public Product(Long id, String title, BigDecimal price, ArrayList<Category> categories) {
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", categories=" + categories +
                '}';
    }
}
