package com.example.MERCHANT.service.impl;

import com.example.MERCHANT.dto.MerchantProductDTO;
import com.example.MERCHANT.entity.MerchantDetails;
import com.example.MERCHANT.entity.MerchantProduct;
import com.example.MERCHANT.repository.MerchantDetailsRepository;
import com.example.MERCHANT.repository.MerchantProductRepository;
import com.example.MERCHANT.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    MerchantProductRepository merchantProductRepository;
    @Autowired
    MerchantDetailsRepository merchantDetailsRepository;

    @Override
    public List<MerchantProduct> findByProductId(String productId) {

       return merchantProductRepository.findByProductId(productId);
    }

    @Override
    public MerchantProduct saveProduct(MerchantProduct merchantProduct) {
            return merchantProductRepository.save(merchantProduct);
    }

    @Override
    public MerchantDetails saveProduct(MerchantDetails merchantDetails) {
        return merchantDetailsRepository.save(merchantDetails);
    }

    @Override
    public MerchantDetails findById(String merchantDetailsId) {
        return null;
    }
}
