package com.example.productmodule.app.gRPC.mapper;

import com.example.grpc.ProductServiceOuterClass;
import com.example.productmodule.app.domain.Category;
import com.example.productmodule.app.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GRpcMapperTest {

    private GRpcMapper gRpcMapper = new GRpcMapper();

    private Category category = new Category(6l, "testCategory");
    private Product product = new Product(1l,"TestProduct", new BigDecimal(100), 1l, new ArrayList<Category>(List.of(category)));

    @Test
    void productToGRpcResponse() {
        ProductServiceOuterClass.ProtoProduct protoProduct = gRpcMapper.productToGRpcResponse(product);


        assertNotNull(protoProduct);
        assertEquals(protoProduct.getId(), product.getId());
        assertEquals(protoProduct.getTitle(), product.getTitle());
        assertEquals(new BigDecimal(protoProduct.getPrice()), product.getPrice());
        assertEquals(protoProduct.getCountInStock(), product.getCount_in_stock());
        assertEquals(protoProduct.getCategories().getCategory(0).getId(), product.getCategories().get(0).getId());
        assertEquals(protoProduct.getCategories().getCategory(0).getTitle(), product.getCategories().get(0).getTitle());
    }
}