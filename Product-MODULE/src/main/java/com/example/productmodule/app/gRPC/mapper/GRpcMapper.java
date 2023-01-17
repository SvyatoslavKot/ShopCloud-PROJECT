package com.example.productmodule.app.gRPC.mapper;

import com.example.grpc.ProductServiceOuterClass;
import com.example.productmodule.app.domain.Category;
import com.example.productmodule.app.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class GRpcMapper {

    public ProductServiceOuterClass.ProtoProduct productToGRpcResponse(Product product){
       List<ProductServiceOuterClass.ProtoCategory> rpcCategories = new ArrayList<>();

        for (Category category : product.getCategories()) {
            ProductServiceOuterClass.ProtoCategory rpcCategory = ProductServiceOuterClass.ProtoCategory.newBuilder()
                    .setId(category.getId())
                    .setTitle(category.getTitle()).build();
            rpcCategories.add(rpcCategory);
        }

        ProductServiceOuterClass.ProtoListOfCategory listOfCategory = ProductServiceOuterClass.ProtoListOfCategory.newBuilder()
                .addAllCategory(rpcCategories)
                .build();

        ProductServiceOuterClass.ProtoProduct grpcProduct = ProductServiceOuterClass.ProtoProduct.newBuilder()
                .setId(product.getId())
                .setTitle(product.getTitle())
                .setPrice(String.valueOf(product.getPrice()))
                .setCountInStock(product.getCount_in_stock())
                .setCategories(listOfCategory)
                .build();

        return grpcProduct;
    }
}
