package com.example.productmodule.app.dto.mappers;

import com.example.productmodule.app.domain.Category;
import com.example.productmodule.app.domain.Product;
import com.example.productmodule.app.dto.ProductDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperDTOTest {

    private ProductMapperDTO mapper = new ProductMapperDTO();

    @Test
    void productToDTO() {
        Category category = new Category(6l, "testCategory");
        Product product = new Product(1l,"TestProduct", new BigDecimal(100), 1l, new ArrayList<Category>(List.of(category)));

        ProductDTO dto = mapper.productToDTO(product);

        assertNotNull(dto);
        assertEquals(dto.getId(), product.getId());
        assertEquals(dto.getTitle(), product.getTitle());
        assertEquals(dto.getPrice(), product.getPrice());
        assertEquals(dto.getCount_in_stock(), product.getCount_in_stock());
        assertEquals(dto.getCategories().get(0).getId(), category.getId());
        assertEquals(dto.getCategories().get(0).getTitle(), category.getTitle());
    }

    @Test
    void productFromDTO() {
        Category category = new Category(6l, "testCategory");
        ProductDTO productDTO = new ProductDTO(1l,"TestProduct", new BigDecimal(100),new ArrayList<Category>(List.of(category)),1l);

        Product product = mapper.productFromDTO(productDTO);

        assertNotNull(product);
        assertEquals(product.getId(), productDTO.getId());
        assertEquals(product.getTitle(), productDTO.getTitle());
        assertEquals(product.getPrice(), productDTO.getPrice());
        assertEquals(product.getCount_in_stock(), productDTO.getCount_in_stock());
        assertEquals(product.getCategories().get(0).getId(), category.getId());
        assertEquals(product.getCategories().get(0).getTitle(), category.getTitle());
    }
}