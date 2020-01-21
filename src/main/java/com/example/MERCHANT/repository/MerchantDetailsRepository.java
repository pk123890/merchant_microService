package com.example.MERCHANT.repository;

import com.example.MERCHANT.dto.MerchantDetailsDTO;
import com.example.MERCHANT.entity.MerchantDetails;
import org.springframework.data.repository.CrudRepository;

public interface MerchantDetailsRepository extends CrudRepository<MerchantDetails,String> {
    public MerchantDetails findByMerchantId(String merchantId);
}
