package com.example.MERCHANT.service.impl;

import com.example.MERCHANT.controller.MerchantControllerProxy;
import com.example.MERCHANT.dto.ProductDTO;
import com.example.MERCHANT.dto.ProductNameDTO;
import com.example.MERCHANT.service.ProductToId;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
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
