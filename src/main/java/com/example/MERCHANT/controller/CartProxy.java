package com.example.MERCHANT.controller;


import com.example.MERCHANT.dto.CartDTO;
import com.example.MERCHANT.dto.MerchantOrderHistoryDTO;
import com.example.MERCHANT.dto.ProductDTO;
import com.google.common.net.HttpHeaders;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "cart", url = "http://10.177.7.167:8082")
public interface CartProxy {
    @PostMapping("/orderHistoryByMerchant")
    List<MerchantOrderHistoryDTO> orderHistoryById(@RequestBody MerchantOrderHistoryDTO merchantOrderHistoryDTO);

    @GetMapping("/checkout")
    List<CartDTO> checkout(@RequestHeader HttpHeaders headers);
}
