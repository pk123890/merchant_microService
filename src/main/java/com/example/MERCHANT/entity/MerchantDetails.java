package com.example.MERCHANT.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MerchantDetails implements Serializable {
    private String merchantEmailId;
    private String merchantName;
    private String gstIN;
    private double merchantRating;

    public MerchantDetails(String merchantEmailId, String merchantName, String gstIN, double merchantRating) {
        this.merchantEmailId = merchantEmailId;
        this.merchantName = merchantName;
        this.gstIN = gstIN;
        this.merchantRating = merchantRating;
    }
}
