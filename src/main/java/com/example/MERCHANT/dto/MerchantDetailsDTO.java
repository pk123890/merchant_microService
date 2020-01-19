package com.example.MERCHANT.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MerchantDetailsDTO implements Serializable {
    private String merchantEmailId;
    private String merchantName;
    private String gstIN;
    private double merchantRating;

    public MerchantDetailsDTO(String merchantEmailId, String merchantName, String gstIN, double merchantRating) {
        this.merchantEmailId = merchantEmailId;
        this.merchantName = merchantName;
        this.gstIN = gstIN;
        this.merchantRating = merchantRating;
    }
}
