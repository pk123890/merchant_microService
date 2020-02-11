package com.example.MERCHANT.controller;

import com.example.MERCHANT.dto.UserProfile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="login",url = "http://10.177.7.130:8083")
public interface APIProxy {

    @GetMapping("authenticate/user/me")
    public UserProfile getCurrentUser(@RequestHeader("Auth") String bearerToken);
}
