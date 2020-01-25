package com.example.MERCHANT.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MerchantOrderHistoryDTO {
    private String merchantId;

    private String productName;

    private String timestamp;

    private int counter;

    private Double price;

}
