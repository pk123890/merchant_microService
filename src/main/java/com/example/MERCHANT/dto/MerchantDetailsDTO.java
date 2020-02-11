package com.example.MERCHANT.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDetailsDTO implements Serializable {
    private String merchantId;
    private String merchantName;
    private String merchantEmailId;
    private double merchantRating;

}
