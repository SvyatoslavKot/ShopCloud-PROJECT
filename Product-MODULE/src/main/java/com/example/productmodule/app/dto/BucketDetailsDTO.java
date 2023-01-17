package com.example.productmodule.app.dto;

import com.example.productmodule.app.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDetailsDTO {
    private String title;
    private Long productId;
    private BigDecimal price;
    private BigDecimal amount;
    private Double sum;

    public BucketDetailsDTO(Product product){
        this.title = product.getTitle();
        this.productId = product.getId();
        this.price = product.getPrice();
        this.amount = new BigDecimal(1.0);
        this.sum = Double.valueOf(product.getPrice().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BucketDetailsDTO that = (BucketDetailsDTO) o;
        return Objects.equals(title, that.title) && Objects.equals(productId, that.productId) && Objects.equals(price, that.price) && Objects.equals(amount, that.amount) && Objects.equals(sum, that.sum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, productId, price, amount, sum);
    }
}