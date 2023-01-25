package com.example.productmodule.app.service.documentSevice;

import com.example.productmodule.app.domain.Category;
import com.example.productmodule.app.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringParser {

    public ProductDTO parseStringToProductDto (String stringLine) {
         String title = null;
         BigDecimal price = null;
         List<Category> categories = null;
         Long count_in_stock = null;


        if (stringLine != null && !stringLine.isEmpty()){
            String[] strings = stringLine.split(" ");
            for (String s : strings) {
                if (s.contains("title=")){
                    title = s.split("title=")[1];
                }
                if (s.contains("categories=")){
                    String categoriesList = s.split("categories=")[1];
                    //categories = Arrays.stream(categoriesList.split(",")).collect(Collectors.toList());

                    //categories.addAll(Arrays.stream(category).collect());
                }
                if (s.contains("price=")){
                    price = new BigDecimal(s.split("price=")[1]);
                }
                if (s.contains("count_in_stock=")){
                    count_in_stock = Long.valueOf(s.split("count_in_stock=")[1]);
                }
            }
            ProductDTO productDTO = new ProductDTO(null,title, price, categories, count_in_stock);
            return productDTO;
        }
        return null;

    }
}
