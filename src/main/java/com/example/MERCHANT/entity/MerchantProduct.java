package com.example.MERCHANT.entity;

import com.example.MERCHANT.dto.MerchantProductIdDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(MerchantProductIdDTO.class)
public class MerchantProduct implements Serializable {
    @Id
    private String merchantEmailId;
    @Id
    private String merchantName;

    private String gstIN;
    private double merchantRating;

    public MerchantProduct(String merchantEmailId, String merchantName, String gstIN, double merchantRating) {
        this.merchantEmailId = merchantEmailId;
        this.merchantName = merchantName;
        this.gstIN = gstIN;
        this.merchantRating = merchantRating;
    }
}
