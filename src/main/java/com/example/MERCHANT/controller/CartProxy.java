package com.example.MERCHANT.controller;

import com.example.MERCHANT.dto.MerchantOrderHistoryDTO;
import com.example.MERCHANT.dto.ProductDTO;
import com.example.MERCHANT.dto.ProductsInCartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "cart", url = "http://172.16.20.172:8082")
public interface CartProxy {
    @GetMapping("/customer/{merchantId}")
    List<ProductsInCartDTO> merchantViewCustomer(@PathVariable("merchantId") String merchantId);

}
