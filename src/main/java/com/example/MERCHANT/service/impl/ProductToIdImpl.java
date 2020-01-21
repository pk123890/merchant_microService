package com.example.MERCHANT.service.impl;

import com.example.MERCHANT.controller.MerchantControllerProxy;
import com.example.MERCHANT.dto.ProductDTO;
import com.example.MERCHANT.service.ProductToId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductToIdImpl implements ProductToId {
    @Autowired
    MerchantControllerProxy merchantControllerProxy;

    @Override
    public List<String> viewProductsByCategoryId(String id) {
        List<ProductDTO> productById = merchantControllerProxy.viewProductsByCategoryId(id).stream().collect(Collectors.toList());
        List<String> productNames = productById.stream().map(ProductDTO::getProductName).collect(Collectors.toList());
        return productNames;
    }
}
