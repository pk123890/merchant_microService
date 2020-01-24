package com.example.MERCHANT.service.impl;
import com.example.MERCHANT.controller.CartProxy;
import com.example.MERCHANT.controller.ProductProxy;
import com.example.MERCHANT.dto.*;
import com.example.MERCHANT.entity.MerchantDetails;
import com.example.MERCHANT.entity.MerchantProduct;
import com.example.MERCHANT.repository.MerchantDetailsRepository;
import com.example.MERCHANT.repository.MerchantProductRepository;
import com.example.MERCHANT.service.MerchantService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
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

    @Autowired
    ProductProxy productProxy;

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
    public CartResponseDTO editInventoryAfterOrder(CartDTO cartDTO) {
        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        cartResponseDTO.setSuccess(true);
        List<ProductsInCartDTO> productsInCartList = cartDTO.getProductDTO();
        List<String> stockOutProductIds = new ArrayList<>();
        productsInCartList.forEach(productsInCartDTO -> {
            List<MerchantProduct> merchantProducts = merchantProductRepository.findByMerchantDetailsAndProductId(merchantDetailsRepository.findByMerchantId(productsInCartDTO.getMerchantId()), productsInCartDTO.getProductId());
            if (!CollectionUtils.isEmpty(merchantProducts)) {
                MerchantProduct merchantProduct = merchantProducts.get(0);
                if ((merchantProduct.getStock() - productsInCartDTO.getCounter()) >= 0) {
                    merchantProduct.setStock(merchantProduct.getStock() - productsInCartDTO.getCounter());
                } else {
                    cartResponseDTO.setSuccess(false);
                    stockOutProductIds.add(productsInCartDTO.getProductId());
                }
                merchantProductRepository.save(merchantProduct);

            }
        });
        cartResponseDTO.setProductId(stockOutProductIds);
        return cartResponseDTO;
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
    private static final String TOPIC="Products";
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    @Override
    public void editProduct(MerchantProductDTO merchantProductDTO) {


        List<MerchantProduct> merchantProduct =merchantProductRepository.findByMerchantDetailsAndProductId(merchantDetailsRepository.findByMerchantId(merchantProductDTO.getMerchantId()), merchantProductDTO.getProductId());
        merchantProduct.get(0).setPrice(merchantProductDTO.getPrice());
        merchantProduct.get(0).setStock(merchantProductDTO.getStock());
        merchantProductRepository.save(merchantProduct.get(0));
        try {
            kafkaTemplate.send(TOPIC, (new ObjectMapper()).writeValueAsString(merchantProduct));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void editInventory(MerchantProductDTO merchantProductDTO) {
        MerchantDetails merchantDetails = new MerchantDetails();
        MerchantProduct merchantProduct=new MerchantProduct();
        merchantProduct.setProductId(merchantProductDTO.getProductId());
        merchantProduct.setStock(merchantProductDTO.getStock());
        merchantProduct.setPrice(merchantProductDTO.getPrice());
        String id=merchantProductDTO.getMerchantId();
        merchantDetails.setMerchantId(id);
        merchantProduct.setMerchantDetails(merchantDetails);
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
