package com.example.MERCHANT.controller;
import com.example.MERCHANT.dto.*;
import com.example.MERCHANT.entity.MerchantDetails;
import com.example.MERCHANT.entity.MerchantProduct;
import com.example.MERCHANT.repository.MerchantDetailsRepository;
import com.example.MERCHANT.service.CategoryToId;
import com.example.MERCHANT.service.MerchantService;
import com.example.MERCHANT.service.ProductToId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MerchantController {
    @Autowired
    CategoryToId categoryToId;
    @Autowired
    ProductToId productToId;
    @Autowired
    MerchantService merchantService;
    @Autowired
    ProductProxy productProxy;

    @Autowired
    MerchantDetailsRepository merchantDetailsRepository;

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

    @GetMapping("/editInventory{merchantId}/{productId}/{price}/{stock}")
    public void editProduct(@PathVariable("merchantId") String merchantId, @PathVariable("productId") String productId, @PathVariable("price") Double price, @PathVariable("stock") int stock) {


    }

    @GetMapping("viewProductIdByMerchantId/{merchantId}")
    public List<String> viewProduct(@PathVariable("merchantId") String merchantId) {
        return merchantService.findByMerchantId(merchantId);
    }

    @GetMapping("viewProductsByMerchantId/{merchantId}")
    public List<ProductsDTO> viewProductsByMerchantId(@PathVariable("merchantId") String merchantId) {
        return productProxy.getProductWithStock(merchantId);
    }

    @GetMapping("viewProductByProductIdAndMerchantId/{productId}/{merchantId}")
    public List<MerchantProduct> viewProductByProductIdAndMerchantId(@PathVariable("productId") String productId, @PathVariable("merchantId") String merchantId) {
        return merchantService.findByProductId(productId);
    }

    @PostMapping("/productMerchant")
    public Map<String, MerchantProduct> viewPriceAndStockByProductId(@RequestBody List<CartDTO> cartDTO) {
        Map<String, MerchantProduct> merchantProductMap = new HashMap<>();
        cartDTO.forEach(cartDTO1 -> {
            MerchantProduct merchantProducts = merchantService.findByProductIdAndMerchant(cartDTO1.getProductId(), cartDTO1.getMerchantId());
            if (null != merchantProducts) {
                merchantProductMap.put(merchantProducts.getProductId() + "_" + merchantProducts.getMerchantDetails().getMerchantId(), merchantProducts);
            }
        });
        return merchantProductMap;
//        List<String> merchantIds=cartDTO.stream().map(CartDTO::getMerchantId).collect(Collectors.toList());
//        List<String> productIds=cartDTO.stream().map(CartDTO::getProductId).collect(Collectors.toList());
//        Map<String,String> merchantAndProduct = new HashMap<>();
//        for(int i=0;i<merchantIds.size();i++) {
//            merchantAndProduct.put(merchantIds.get(i), productIds.get(i));
//        }
//
//        MerchantProductDTO merchantProductDTO;
    }

    @GetMapping("/viewCustomer")
    public List<MerchantOrderHistoryDTO> viewCustomer(@RequestBody MerchantOrderHistoryDTO merchantOrderHistoryDTO) {
        return merchantService.viewCustomer(merchantOrderHistoryDTO);
    }

    @GetMapping("/addProductByMerchant/{productId}/{merchantId}/{price}/{stock}")
    public String addProduct(@PathVariable("productId") String productId, @PathVariable("merchantId") String merchantId, @PathVariable("price") Double price, @PathVariable("stock") int stock) {
       merchantService.addProduct(productId,merchantId,price,stock);
        return productId;
    }
    @PostMapping("/editInventory/")
    public void editInventory(@RequestBody MerchantProductDTO merchantProductDTO){

        merchantService.editInventory(merchantProductDTO);
    }

    @GetMapping("/updateStockAfterOrder/{userIdHeader}")
    public void updateInventory(@PathVariable("userIdHeader") String userIdHeader) {
        merchantService.editInventoryAfterOrder(userIdHeader);
    }

}
