package com.example.productmodule.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties
public class OrderRequest implements Serializable {
    private List<ProductDTO>productDTOS;
    private String userMail;

}
