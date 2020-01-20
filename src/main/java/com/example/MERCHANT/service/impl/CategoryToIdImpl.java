package com.example.MERCHANT.service.impl;

import com.example.MERCHANT.controller.MerchantControllerProxy;
import com.example.MERCHANT.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryToIdImpl implements com.example.MERCHANT.service.CategoryToId {
    @Autowired
    MerchantControllerProxy merchantControllerProxy;
    @Override
    public List<CategoryDTO> getAllCategories() {
        List Categories = merchantControllerProxy.getAllCategories().stream().collect(Collectors.toList());
        return Categories;
    }
}
