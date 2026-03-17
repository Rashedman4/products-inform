package com.productsapp.productsinform.controller;


import com.productsapp.productsinform.model.dto.PageResponse;
import com.productsapp.productsinform.model.dto.ProductDto;
import com.productsapp.productsinform.model.dto.ProductRequest;
import com.productsapp.productsinform.service.ProductServiceInterfaceV2;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/products")
public class ProductControllerV2 {
    private final ProductServiceInterfaceV2 productService;

    @PostMapping
    public ProductDto addProduct(@Valid @RequestBody ProductRequest req) {
        return productService.addProduct(req);
    }
    @GetMapping("/id/{id}")
    public ProductDto getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/name/{name}")
    public PageResponse<ProductDto> getProductByName(@PathVariable String name,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(defaultValue = "id") String sortBy,
                                                     @RequestParam(defaultValue = "asc") String direction) {
        return productService.getProductByName(name,page,size,sortBy,direction);
    }
    @GetMapping("/category/{category}")
    public PageResponse<ProductDto> getProductByCategory(@PathVariable String category,@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(defaultValue = "id") String sortBy,
                                                 @RequestParam(defaultValue = "asc") String direction) {
        return productService.getProductByCategory(category,page,size,sortBy,direction);
    }
    @GetMapping("/price/{price}")
    public PageResponse<ProductDto> getProductByPrice(@PathVariable BigDecimal price,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "id") String sortBy,
                                              @RequestParam(defaultValue = "asc") String direction) {
        return productService.getProductByPrice(price,page,size,sortBy,direction);
    }

    @GetMapping
    public PageResponse<ProductDto> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "id") String sortBy,
                                           @RequestParam(defaultValue = "asc") String direction) {
        return productService.getAllProducts(page,size,sortBy,direction);
    }

    @DeleteMapping("/{id}")
    public ProductDto deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }


    @PutMapping("/{id}")
    public ProductDto updateProduct(@Valid @RequestBody ProductRequest req,@PathVariable long id) {
        return productService.updateProduct(req,id);
    }


}
