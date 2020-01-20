package com.example.MERCHANT.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
@AllArgsConstructor
public class MerchantDetails implements Serializable {
    @Id
    private String merchantEmailId;
    private String merchantName;
    private String gstIN;
    private double merchantRating;


}
