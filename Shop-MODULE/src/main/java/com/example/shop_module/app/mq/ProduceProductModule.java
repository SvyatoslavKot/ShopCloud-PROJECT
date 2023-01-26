package com.example.shop_module.app.mq;


import com.example.shop_module.app.dto.BucketDTO;
import com.example.shop_module.app.dto.OrderDTO;
import com.example.shop_module.app.dto.ProductDTO;


import java.util.List;


public interface ProduceProductModule {

    ProductDTO getProductItem(Long id);
    void addToBucketByID(Long productId, String mail);
    BucketDTO getBucketByUser(String email);
    void removeFromBucket(Long productId, String mail);
    List<ProductDTO> getAll();
    void addProduct(ProductDTO dto);
    OrderDTO commitBucketToOrder(String email);
    void updateProduct(ProductDTO updateProduct0);

}
