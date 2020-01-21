package com.example.MERCHANT.controller;

import com.example.MERCHANT.dto.*;
import com.example.MERCHANT.entity.MerchantDetails;
import com.example.MERCHANT.entity.MerchantProduct;
import com.example.MERCHANT.service.CategoryToId;
import com.example.MERCHANT.service.MerchantService;
import com.example.MERCHANT.service.ProductToId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MerchantController {
    @Autowired
    CategoryToId categoryToId;
    @Autowired
    ProductToId productToId;
    @Autowired
    MerchantService merchantService;
    @Autowired
    MerchantControllerProxy merchantControllerProxy;

    @GetMapping("/addProduct")
    public List<CategoryDTO> getAllCategories() {
        return categoryToId.getAllCategories();
    }

    @GetMapping("/product/{id}")
    public List<String> viewProductsByCategoryId(@PathVariable("id") String id) {
        return productToId.viewProductsByCategoryId(id);
    }

    @GetMapping("/merchant/{productId}")
    public List<MerchantListingDTO> viewMerchantByProductId(@PathVariable("productId") String productId) {
        List<MerchantProduct> merchantProductList = merchantService.findByProductId(productId);
        List<MerchantListingDTO> merchantListingDTOList = merchantProductList.stream()
                .map(merchantProduct -> createMerchantListingDTO(merchantProduct)).collect(Collectors.toList());
        return merchantListingDTOList;
    }

    private MerchantListingDTO createMerchantListingDTO(MerchantProduct merchantProduct) {
        return MerchantListingDTO.builder()
                .merchantName(merchantProduct.getMerchantDetails().getMerchantName())
                .productId(merchantProduct.getProductId())
                .price((int) merchantProduct.getPrice())
                .stock(merchantProduct.getStock())
                .rating(merchantProduct.getMerchantDetails().getMerchantRating())
                .merchantId(merchantProduct.getMerchantDetails().getMerchantId())
                .build();
    }

    @PostMapping("/addMerchantProduct")
    public ResponseEntity<String> addMerchantProduct(@RequestBody MerchantProductDTO merchantProductDTO) {
        MerchantProduct merchantProduct = new MerchantProduct();
        MerchantDetails merchantDetails = new MerchantDetails();
        merchantDetails.setMerchantId(merchantProductDTO.getMerchantId());
        BeanUtils.copyProperties(merchantProductDTO, merchantProduct);
        merchantProduct.setMerchantDetails(merchantDetails);
        MerchantProduct merchantProductCreated = merchantService.saveProduct(merchantProduct);
        return new ResponseEntity<String>(merchantProductCreated.getProductId(), HttpStatus.CREATED);

    }

    @PostMapping("/addMerchantDetails")
    public ResponseEntity<String> addMerchantDetails(@RequestBody MerchantDetailsDTO merchantDetailsDTO) {
        MerchantDetails merchantDetails = new MerchantDetails();
        BeanUtils.copyProperties(merchantDetailsDTO, merchantDetails);
        MerchantDetails merchantDetailsCreated = merchantService.saveProduct(merchantDetails);
        return new ResponseEntity<String>(merchantDetailsCreated.getMerchantId(), HttpStatus.CREATED);

    }

    @GetMapping("/editInventory/{productId}/{price}/{stock}")
    public void addInventory(@PathVariable("productId") String productId, @PathVariable("price") Double price, @PathVariable("stock") int stock) {
        MerchantProduct merchantProduct;

        merchantProduct = merchantService.findByProductId(productId).get(0);
        merchantProduct.setPrice(price);
        merchantProduct.setStock(stock);
        merchantService.saveProduct(merchantProduct);

    }

    @GetMapping("viewProductIdByMerchantId/{merchantId}")
    public List<String> viewProduct(@PathVariable("merchantId") String merchantId){
        return merchantService.findByMerchantId(merchantId);
    }

    @GetMapping("viewProductsByMerchantId/{merchantId}")
    public List<ProductsDTO> viewProductsByMerchantId(@PathVariable("merchantId") String merchantId){
        return merchantControllerProxy.getProductWithStock(merchantId);
    }


}
