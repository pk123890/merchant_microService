package com.example.MERCHANT.service.impl;

import com.example.MERCHANT.controller.CartProxy;
import com.example.MERCHANT.dto.CartDTO;
import com.example.MERCHANT.dto.MerchantOrderHistoryDTO;
import com.example.MERCHANT.dto.MerchantProductDTO;
import com.example.MERCHANT.entity.MerchantDetails;
import com.example.MERCHANT.entity.MerchantProduct;
import com.example.MERCHANT.repository.MerchantDetailsRepository;
import com.example.MERCHANT.repository.MerchantProductRepository;
import com.example.MERCHANT.service.MerchantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    MerchantProductRepository merchantProductRepository;
    @Autowired
    MerchantDetailsRepository merchantDetailsRepository;

    @Autowired
    CartProxy cartProxy;

    @Override
    public List<MerchantProduct> findByProductId(String productId) {
        return merchantProductRepository.findByProductId(productId);
    }

    @Override
    public MerchantProduct findByProductIdAndMerchant(String productId, String merchantId) {
        AtomicReference<List<MerchantProduct>> merchantProducts = new AtomicReference<>();
        Optional<MerchantDetails> merchantDetails = merchantDetailsRepository.findById(merchantId);
        merchantDetails.ifPresent(merchantDetails1 -> {
            merchantProducts.set(merchantProductRepository.findByMerchantDetailsAndProductId(merchantDetails1, productId));
        });
        if (!CollectionUtils.isEmpty(merchantProducts.get())) {
            return merchantProducts.get().get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<MerchantOrderHistoryDTO> viewCustomer(MerchantOrderHistoryDTO merchantOrderHistoryDTO) {
        return cartProxy.orderHistoryById(merchantOrderHistoryDTO);
    }

    @Override
    public void editInventoryAfterOrder(String userIdHeader) {
        List<CartDTO> productsInCart = cartProxy.checkout(userIdHeader);
        productsInCart.forEach(cartDTO -> {
            List<MerchantProduct> merchantProductDTOList = merchantProductRepository.findByMerchantDetailsAndProductId(merchantDetailsRepository.findByMerchantId(cartDTO.getMerchantId()), cartDTO.getProductId());
            if (!CollectionUtils.isEmpty(merchantProductDTOList)) {
                MerchantProduct merchantProduct = merchantProductDTOList.get(0);
                merchantProduct.setStock(merchantProduct.getStock() - cartDTO.getCounter());
                merchantProductRepository.save(merchantProduct);
            }
        });
    }

    @Override
    public MerchantProduct addProduct(String productId,String merchantId,Double price,int stock) {
        MerchantProduct merchantProduct = new MerchantProduct();
        MerchantDetails merchantDetails = merchantDetailsRepository.findByMerchantId(merchantId);

        merchantProduct.setMerchantDetails(merchantDetails);
        merchantProduct.setProductId(productId);
        merchantProduct.setPrice(price);
        merchantProduct.setStock(stock);
        return merchantProductRepository.save(merchantProduct);

    }

    @Override
    public void editProduct(String merchantId, String productId, Double price, int stock) {
        MerchantProduct merchantProduct;

        merchantProduct = merchantProductRepository.findByProductId(productId).get(0);
        merchantProduct.setPrice(price);
        merchantProduct.setStock(stock);
        merchantProductRepository.save(merchantProduct);
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

        MerchantDetails merchantDetails = merchantDetailsRepository.findByMerchantId(merchantId);
        List<MerchantProduct> merchantProducts = merchantProductRepository.findByMerchantDetails(merchantDetails);
        List<String> productIds = merchantProducts.stream().map(MerchantProduct::getProductId).collect(Collectors.toList());
//        List<ProductDetailsByMerchantDTO> productDetailsByMerchantDTOS =
//                merchantProducts.stream().map(this:: productDetailsByMerchant).collect(Collectors.toList());

        return productIds;


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
