package com.example.MERCHANT.service.impl;

import com.example.MERCHANT.dto.MerchantProductDTO;
import com.example.MERCHANT.dto.ProductDetailsByMerchantDTO;
import com.example.MERCHANT.entity.MerchantDetails;
import com.example.MERCHANT.entity.MerchantProduct;
import com.example.MERCHANT.repository.MerchantDetailsRepository;
import com.example.MERCHANT.repository.MerchantProductRepository;
import com.example.MERCHANT.service.MerchantService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Override
    public List<String> findByMerchantId(String merchantId) {
        ObjectMapper objectMapper = new ObjectMapper();

        MerchantDetails merchantDetails =  merchantDetailsRepository.findByMerchantId(merchantId);
        List<MerchantProduct> merchantProducts  = merchantProductRepository.findByMerchantDetails(merchantDetails);
        List<String> productId= merchantProducts.stream().map(MerchantProduct::getProductId).collect(Collectors.toList());
//        List<ProductDetailsByMerchantDTO> productDetailsByMerchantDTOS =
//                merchantProducts.stream().map(this:: productDetailsByMerchant).collect(Collectors.toList());

        return productId;


    }

//    public ProductDetailsByMerchantDTO productDetailsByMerchant(MerchantProduct merchantProduct){
//        ProductDetailsByMerchantDTO productDetailsByMerchantDTO1 = new ProductDetailsByMerchantDTO();
//
//        productDetailsByMerchantDTO1.setProductId(merchantProduct.getProductId());
//        productDetailsByMerchantDTO1.setPrice(merchantProduct.getPrice());
//        productDetailsByMerchantDTO1.setStock(merchantProduct.getStock());
//
//        return productDetailsByMerchantDTO1;
//    }
}
