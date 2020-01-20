package com.example.MERCHANT.controller;

import com.example.MERCHANT.dto.CategoryDTO;
import com.example.MERCHANT.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product", url = "http://172.16.20.51:8082")
public interface MerchantControllerProxy {
    @GetMapping("category/getAllCategories")
    List<CategoryDTO> getAllCategories();
    @GetMapping("product/viewProductsByCategoryId/{id}")
    List<ProductDTO> viewProductsByCategoryId(@PathVariable("id") String id);

}
