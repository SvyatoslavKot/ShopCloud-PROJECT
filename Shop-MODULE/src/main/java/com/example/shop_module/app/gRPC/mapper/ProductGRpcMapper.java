package com.example.shop_module.app.gRPC.mapper;


import com.example.grpc.ProductServiceOuterClass;
import com.example.shop_module.app.domain.Category;
import com.example.shop_module.app.dto.ProductDTO;
import com.example.shop_module.app.wrapper.PageableResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductGRpcMapper {


    public ProductDTO fromGRpc (ProductServiceOuterClass.ProtoProduct protoProduct) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(protoProduct.getId());
        productDTO.setPrice(new BigDecimal(protoProduct.getPrice()));
        productDTO.setTitle(protoProduct.getTitle());
        List<Category> categories = new ArrayList<>();
        for (ProductServiceOuterClass.ProtoCategory category : protoProduct.getCategories().getCategoryList()){
            Category c = new Category(category.getId(), category.getTitle());
            categories.add(c);
        }

        productDTO.setCategories(categories);

        return productDTO;
    }

    public PageableResponse<ProductDTO> pageFromGRpc(ProductServiceOuterClass.ProtoPageableResponse protoPageableResponse) {
        List<ProductDTO> productDTOList = new ArrayList<>();

        for (ProductServiceOuterClass.ProtoProduct protoProduct : protoPageableResponse.getContent().getProductList()){
                productDTOList.add(fromGRpc(protoProduct));
            }


        PageableResponse<ProductDTO> pageableResponse = new PageableResponse<ProductDTO>(
                productDTOList, protoPageableResponse.getNumber(),
                protoPageableResponse.getSize(),
                protoPageableResponse.getTotalElements(),
                protoPageableResponse.getLast(),
                protoPageableResponse.getFirst(),
                protoPageableResponse.getTotalPages(),
                protoPageableResponse.getNumberOfElements(),
                null,
                null
        );

        return pageableResponse;
    }

    public ProductServiceOuterClass.ProtoProduct toProtoProduct (ProductDTO productDTO) {

        return ProductServiceOuterClass.ProtoProduct.newBuilder()
                .setId(productDTO.getId())
                .setTitle(productDTO.getTitle())
                .setPrice(productDTO.getPrice().toString())
                .setCategories(ProductServiceOuterClass.ProtoListOfCategory.newBuilder().addAllCategory(toProtoCategory(productDTO.getCategories())).build())
                .build();
    }


    public List<ProductServiceOuterClass.ProtoCategory> toProtoCategory (List<Category> categoryList) {
        List<ProductServiceOuterClass.ProtoCategory> protoCategoryList = new ArrayList<>();

        if (categoryList != null && !categoryList.isEmpty()) {
            for (Category category : categoryList) {
                ProductServiceOuterClass.ProtoCategory protoCategory = ProductServiceOuterClass.ProtoCategory.newBuilder()
                        .setId(category.getId())
                        .setTitle(category.getTitle())
                        .build();
                protoCategoryList.add(protoCategory);
            }
        }
        return protoCategoryList;
    }


}
