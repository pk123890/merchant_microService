package com.example.MERCHANT.controller;


import com.example.MERCHANT.dto.ProductsInCartDTO;
import com.example.MERCHANT.dto.MerchantOrderHistoryDTO;
import com.google.common.net.HttpHeaders;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "cart", url = "http://172.16.20.172:8082")
public interface CartProxy {
    @PostMapping("/orderHistoryByMerchant")
    List<MerchantOrderHistoryDTO> orderHistoryById(@RequestBody MerchantOrderHistoryDTO merchantOrderHistoryDTO);

    @GetMapping("order/checkout")
    List<ProductsInCartDTO> checkout(@RequestHeader("Auth") HttpHeaders httpHeaders);
}
