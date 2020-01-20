package com.example.MERCHANT.controller;

import com.example.MERCHANT.dto.CategoryDTO;
import com.example.MERCHANT.dto.MerchantProductDTO;
import com.example.MERCHANT.dto.ProductDTO;
import com.example.MERCHANT.entity.MerchantProduct;
import com.example.MERCHANT.service.CategoryToId;
import com.example.MERCHANT.service.MerchantService;
import com.example.MERCHANT.service.ProductToId;
import com.example.MERCHANT.service.impl.MerchantServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class MerchantController {
    @Autowired
    CategoryToId categoryToId;
    @Autowired
    ProductToId productToId;
    @Autowired
    MerchantService merchantService;

    @GetMapping("/addProduct")
    public List<CategoryDTO> getAllCategories() {
        return categoryToId.getAllCategories();
    }

    @GetMapping("/product/{id}")
    public List<String> viewProductsByCategoryId(@PathVariable("id") String id) {
        return productToId.viewProductsByCategoryId(id);
    }

    @GetMapping("/merchant/{productId}")
    public List<MerchantProduct> viewMerchantByProductId(@PathVariable("productId") String productId) {
        return merchantService.findByProductId(productId);
    }

    @PostMapping("/addMerchantProduct")
    public ResponseEntity<String> addMerchantProduct(@RequestBody MerchantProductDTO merchantProductDTO){
        MerchantProduct merchantProduct=new MerchantProduct();
        BeanUtils.copyProperties(merchantProductDTO,merchantProduct);
        MerchantProduct merchantProductCreated=merchantService.saveProduct(merchantProduct);
        return new ResponseEntity<String>(merchantProductCreated.getProductId(),HttpStatus.CREATED);

    }

//    @GetMapping("/viewProductsByCategoryId/{categoryId}")
//    public List<ProductDTO> getProductsByCategory(@PathVariable("categoryId") String categoryId){
//
//
//
//    }


}
