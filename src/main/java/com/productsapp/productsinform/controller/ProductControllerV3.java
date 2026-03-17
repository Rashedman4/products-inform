package com.productsapp.productsinform.controller;

import com.productsapp.productsinform.model.dto.PageResponse;
import com.productsapp.productsinform.model.dto.ProductDto;
import com.productsapp.productsinform.service.ProductServiceV3;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v3/products")
public class ProductControllerV3 {
    private final ProductServiceV3 productServiceV3;
    @GetMapping
    public PageResponse<ProductDto> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return productServiceV3.searchProducts(name, category, minPrice, maxPrice, page, size, sortBy, direction);
    }

}
