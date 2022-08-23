package com.example.shop_module.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BucketDTO {
    private int amountProducts;
    private Double sum;
    private List<BucketDetailsDTO> bucketDetails = new ArrayList<>();

    public void aggregate() {
        this.amountProducts = bucketDetails.size();
        this.sum = bucketDetails.stream()
                .map(BucketDetailsDTO :: getSum)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
