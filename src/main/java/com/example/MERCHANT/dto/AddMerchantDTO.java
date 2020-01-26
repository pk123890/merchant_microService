package com.example.MERCHANT.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddMerchantDTO {
    private String productName;
    private String productDescription;
    private Map productAttribute;
    private String productUsp;
    private String imageUrl;
    private String categoryId;
    private Double price;
    private int stock;
    private String merchantId;
}
