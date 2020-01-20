package com.example.MERCHANT.service.impl;

import com.example.MERCHANT.entity.MerchantProduct;
import com.example.MERCHANT.repository.MerchantRepository;
import com.example.MERCHANT.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    MerchantRepository merchantRepository;

    @Override
    public List<MerchantProduct> findByProductId(String productId) {
       return merchantRepository.findByProductId(productId);
    }

    @Override
    public MerchantProduct saveProduct(MerchantProduct merchantProduct) {
        return merchantRepository.save(merchantProduct);
    }

}
