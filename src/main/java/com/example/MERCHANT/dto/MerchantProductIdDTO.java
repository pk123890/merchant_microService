package com.example.MERCHANT.dto;

import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
public class MerchantProductIdDTO implements Serializable {
    private String merchantId;
    private String productId;

    public MerchantProductIdDTO(String merchantId, String productId) {
        this.merchantId = merchantId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchantProductIdDTO that = (MerchantProductIdDTO) o;
        return Objects.equals(merchantId, that.merchantId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(merchantId, productId);
    }
}
