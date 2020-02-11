package com.example.MERCHANT.controller;

import com.example.MERCHANT.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product", url = "http://10.177.7.167:8082")
public interface ProductProxy {
    @GetMapping("category/getAllCategories")
    List<CategoryDTO> getAllCategories();

    @GetMapping("product/viewProductsByCategoryId/{id}")
    List<ProductDTO> viewProductsByCategoryId(@PathVariable("id") String id);

    @GetMapping("product/productMerchant/{merchantId}")
    List<ProductsDTO> getProductWithStock(@PathVariable("merchantId") String merchantId);

    @PostMapping("product/addProductByMerchant")
    MerchantProductDTO addProduct(@RequestBody AddMerchantDTO addMerchantDTO);
}
