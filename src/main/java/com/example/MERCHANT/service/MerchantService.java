package com.example.MERCHANT.service;

import com.example.MERCHANT.dto.*;
import com.example.MERCHANT.entity.MerchantDetails;
import com.example.MERCHANT.entity.MerchantProduct;

import java.util.List;

public interface MerchantService {
    List<MerchantProduct> findByProductId(String productId);

    MerchantProduct saveProduct(MerchantProduct merchantProduct);

    MerchantDetails saveDetails(MerchantDetails merchantDetails);

    List<String> findByMerchantId(String merchantId);

    MerchantProduct findByProductIdAndMerchant(String productId, String merchantId);

    CartResponseDTO editInventoryAfterOrder(CartDTO cartDTO);

    MerchantProduct addProduct(MerchantProductDTO merchantProductDTO);

    void editProduct(MerchantProductDTO merchantProductDTO);

    void editInventory(MerchantProductDTO merchantProductDTO);

    List<MerchantEditProductDTO> viewProductsByCategoryId(String id);

    List<CategoryDTO> getAllCategories();

    double RatingFindByMerchantId(String merchantId);


}
