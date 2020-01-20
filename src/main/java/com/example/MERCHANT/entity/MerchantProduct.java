package com.example.MERCHANT.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
public class MerchantProduct implements Serializable {
    @GeneratedValue @Id
    private int key;
    private String merchantId;
    private String productId;
    private int stock;
    private double price;
}
