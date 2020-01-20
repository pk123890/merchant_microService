package com.example.MERCHANT.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MerchantProductDTO implements Serializable {
    private String merchantId;
    private String productId;
    private int stock;
    private double price;

}
