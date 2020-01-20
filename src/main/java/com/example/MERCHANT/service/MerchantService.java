package com.example.MERCHANT.service;

import com.example.MERCHANT.entity.MerchantProduct;

import java.util.List;

public interface MerchantService {
    List<MerchantProduct> findByProductId(String productId);
    MerchantProduct saveProduct(MerchantProduct merchantProduct);
}
