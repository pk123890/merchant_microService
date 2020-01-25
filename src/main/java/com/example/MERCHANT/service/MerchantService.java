package com.example.MERCHANT.service;

import com.example.MERCHANT.dto.*;
import com.example.MERCHANT.entity.MerchantDetails;
import com.example.MERCHANT.entity.MerchantProduct;
import com.google.common.net.HttpHeaders;

import java.util.List;

public interface MerchantService {
    List<MerchantProduct> findByProductId(String productId);

    MerchantProduct saveProduct(MerchantProduct merchantProductDTO);

    MerchantDetails saveProduct(MerchantDetails merchantDetails);

    List<String> findByMerchantId(String merchantId);

    MerchantProduct findByProductIdAndMerchant(String productId, String merchantId);

    CartResponseDTO editInventoryAfterOrder(CartDTO cartDTO);

    MerchantProduct addProduct(MerchantProductDTO merchantProductDTO);

    void editProduct(MerchantProductDTO merchantProductDTO);

    void editInventory(MerchantProductDTO merchantProductDTO);

    List<String> viewProductsByCategoryId(String id);

    List<CategoryDTO> getAllCategories();

}
