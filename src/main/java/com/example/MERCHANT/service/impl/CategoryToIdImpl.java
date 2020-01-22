package com.example.MERCHANT.service.impl;

import com.example.MERCHANT.controller.ProductProxy;
import com.example.MERCHANT.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryToIdImpl implements com.example.MERCHANT.service.CategoryToId {
    @Autowired
    ProductProxy productProxy;
    @Override
    public List<CategoryDTO> getAllCategories() {
        List Categories = productProxy.getAllCategories().stream().collect(Collectors.toList());
        return Categories;
    }
}
