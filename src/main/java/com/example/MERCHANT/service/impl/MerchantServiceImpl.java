package com.example.MERCHANT.service.impl;


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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    MerchantProductRepository merchantProductRepository;
    @Autowired
    MerchantDetailsRepository merchantDetailsRepository;
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
    public MerchantProduct addProduct(MerchantProductDTO merchantProductDTO) {
        MerchantProduct merchantProduct = new MerchantProduct();
        MerchantDetails merchantDetails = merchantDetailsRepository.findByMerchantId(merchantProductDTO.getMerchantId());

        merchantProduct.setMerchantDetails(merchantDetails);
        merchantProduct.setProductId(merchantProductDTO.getProductId());
        merchantProduct.setPrice(merchantProductDTO.getPrice());
        merchantProduct.setStock(merchantProductDTO.getStock());
        return merchantProductRepository.save(merchantProduct);

    }

    private static final String TOPIC = "UpdatePrice";
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void editProduct(MerchantProductDTO merchantProductDTO) {

        KafkaProductUpdateDTO kafkaProductUpdateDTO=new KafkaProductUpdateDTO();
        List<MerchantProduct> merchantProduct = merchantProductRepository.findByMerchantDetailsAndProductId(merchantDetailsRepository.findByMerchantId(merchantProductDTO.getMerchantId()), merchantProductDTO.getProductId());
        merchantProduct.get(0).setPrice(merchantProductDTO.getPrice());
        merchantProduct.get(0).setStock(merchantProductDTO.getStock());
        kafkaProductUpdateDTO.setPrice(merchantProductDTO.getPrice());
        kafkaProductUpdateDTO.setProductId(merchantProductDTO.getProductId());
        merchantProductRepository.save(merchantProduct.get(0));
        try {
            kafkaTemplate.send(TOPIC, (new ObjectMapper()).writeValueAsString(kafkaProductUpdateDTO));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void editInventory(MerchantProductDTO merchantProductDTO) {
        MerchantDetails merchantDetails = new MerchantDetails();
        MerchantProduct merchantProduct = new MerchantProduct();
        merchantProduct.setProductId(merchantProductDTO.getProductId());
        merchantProduct.setStock(merchantProductDTO.getStock());
        merchantProduct.setPrice(merchantProductDTO.getPrice());
        String id = merchantProductDTO.getMerchantId();
        merchantDetails.setMerchantId(id);
        merchantProduct.setMerchantDetails(merchantDetails);
        merchantProductRepository.save(merchantProduct);
    }

    @Override
    public MerchantProduct saveProduct(MerchantProduct merchantProduct) {
        return merchantProductRepository.save(merchantProduct);
    }

    @Override
    public MerchantDetails saveDetails(MerchantDetails merchantDetails) {
        return merchantDetailsRepository.save(merchantDetails);
    }

    @Override
    public List<String> findByMerchantId(String merchantId) {
        ObjectMapper objectMapper = new ObjectMapper();

        MerchantDetails merchantDetails = merchantDetailsRepository.findByMerchantId(merchantId);
        List<MerchantProduct> merchantProducts = merchantProductRepository.findByMerchantDetails(merchantDetails);
        List<String> productIds = merchantProducts.stream().map(MerchantProduct::getProductId).collect(Collectors.toList());

        return productIds;


    }

    @Override
    public List<MerchantEditProductDTO> viewProductsByCategoryId(String id) {
        List<ProductDTO> productById = productProxy.viewProductsByCategoryId(id).stream().collect(Collectors.toList());
        List<MerchantEditProductDTO> merchantEditProductDTOList=productById.stream().map(productDTO -> {
            MerchantEditProductDTO merchantEditProductDTO = new MerchantEditProductDTO();
            merchantEditProductDTO.setProductId(productDTO.getProductId());
            merchantEditProductDTO.setProductName(productDTO.getProductName());
            return merchantEditProductDTO;
        }).collect(Collectors.toList());
        return merchantEditProductDTOList;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List Categories = productProxy.getAllCategories().stream().collect(Collectors.toList());
        return Categories;
    }

    @Override
    public double RatingFindByMerchantId(String merchantId) {

        MerchantDetails merchantDetails=merchantDetailsRepository.findByMerchantId(merchantId);
        return merchantDetails.getMerchantRating();
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
