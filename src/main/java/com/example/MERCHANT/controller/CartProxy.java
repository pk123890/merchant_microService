package com.example.MERCHANT.controller;


import com.example.MERCHANT.dto.MerchantOrderHistoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "cart", url = "http://10.177.7.167:8082")
public interface CartProxy {
    @PostMapping("/orderHistoryByMerchant")
    List<MerchantOrderHistoryDTO> orderHistoryById(@RequestBody MerchantOrderHistoryDTO merchantOrderHistoryDTO);
}
