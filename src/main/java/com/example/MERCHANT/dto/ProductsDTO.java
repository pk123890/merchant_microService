package com.example.MERCHANT.dto;

import lombok.*;

import java.util.Map;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductsDTO {
    private String productId;
    private String productName;
    private String productDescription;
    private Map productAttribute;
    private String imageUrl;
    private int stock;
    private Double price;
}
