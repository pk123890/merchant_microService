package com.example.MERCHANT.service;

import com.example.MERCHANT.dto.MerchantDetailsDTO;
import com.example.MERCHANT.dto.MerchantProductDTO;
import com.example.MERCHANT.dto.ProductDetailsByMerchantDTO;
import com.example.MERCHANT.entity.MerchantDetails;
import com.example.MERCHANT.entity.MerchantProduct;

import java.util.List;

public interface MerchantService {
    List<MerchantProduct> findByProductId(String productId);
    MerchantProduct saveProduct(MerchantProduct merchantProductDTO);
    MerchantDetails saveProduct(MerchantDetails merchantDetails);
    MerchantDetails findById(String merchantDetailsId);
    List<String> findByMerchantId(String merchantId);


}
