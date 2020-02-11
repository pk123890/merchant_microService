package com.example.MERCHANT.controller;

import com.example.MERCHANT.dto.ProductsInCartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "cart", url = "http://10.177.7.166:8082")
public interface CartProxy {
    @GetMapping("/customer/{merchantId}")
    List<ProductsInCartDTO> merchantViewCustomer(@PathVariable("merchantId") String merchantId);

}
