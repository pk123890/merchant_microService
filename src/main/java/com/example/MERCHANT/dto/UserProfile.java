package com.example.MERCHANT.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfile {

    private String id;
    private String name;
    private String email;
    private String imageUrl;
    private String provider;
    private String providerId;
}
