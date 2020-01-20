package com.example.MERCHANT.service;



import com.example.MERCHANT.dto.ProductDTO;

import java.util.List;

public interface ProductToId {
    List<String> viewProductsByCategoryId(String id);
}
