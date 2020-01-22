package com.example.MERCHANT.service;
import com.example.MERCHANT.dto.MerchantOrderHistoryDTO;
import com.example.MERCHANT.entity.MerchantDetails;
import com.example.MERCHANT.entity.MerchantProduct;

import java.util.List;

public interface MerchantService {
    List<MerchantProduct> findByProductId(String productId);
    MerchantProduct saveProduct(MerchantProduct merchantProductDTO);
    MerchantDetails saveProduct(MerchantDetails merchantDetails);
    MerchantDetails findById(String merchantDetailsId);
    List<String> findByMerchantId(String merchantId);
    MerchantProduct findByProductIdAndMerchant(String productId, String merchantId);
    List<MerchantOrderHistoryDTO> viewCustomer(MerchantOrderHistoryDTO merchantOrderHistoryDTO);
    void editInventoryAfterOrder(String userIdHeader);
    MerchantProduct addProduct(String productId,String merchantId,Double price,int stock);
    void editProduct(String merchantId,String productId,Double price,int stock);

}
