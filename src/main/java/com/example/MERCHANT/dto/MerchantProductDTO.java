package com.example.MERCHANT.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MerchantProductDTO implements Serializable {
    private String merchantId;
    private String productId;
    private int stock;
    private double price;

    public MerchantProductDTO(String merchantId, String productId, int stock, double price) {
        this.merchantId = merchantId;
        this.productId = productId;
        this.stock = stock;
        this.price = price;
    }
}
