package com.example.MERCHANT.repository;

import com.example.MERCHANT.dto.MerchantProductDTO;
import com.example.MERCHANT.entity.MerchantDetails;
import com.example.MERCHANT.entity.MerchantProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MerchantProductRepository extends CrudRepository<MerchantProduct,String> {
    List<MerchantProduct> findByProductId(String productId);
    List<MerchantProduct> findByMerchantDetails(MerchantDetails merchantDetails);

}
