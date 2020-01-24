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
public class ProductsInCartDTO implements Serializable {


    private String productId;
    private String merchantId;
    private String productName;
    private String imageUrl;
    private Double price;
    private int counter;
    private int stock;
}
