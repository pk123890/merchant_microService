package com.example.MERCHANT.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO implements Serializable {


    private String userId;
    private String productId;
    private String merchantId;
    private int counter;
}