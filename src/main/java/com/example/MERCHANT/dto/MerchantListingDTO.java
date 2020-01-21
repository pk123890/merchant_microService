package com.example.MERCHANT.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchantListingDTO {

    private String productId;
    private String merchantName;
    private Integer price;
    private Integer stock;
    private Double rating;
    private String merchantId;

}
