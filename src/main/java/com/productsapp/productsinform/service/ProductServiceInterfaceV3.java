package com.productsapp.productsinform.service;

import com.productsapp.productsinform.model.dto.PageResponse;
import com.productsapp.productsinform.model.dto.ProductDto;

import java.math.BigDecimal;

public interface ProductServiceInterfaceV3 {
    PageResponse<ProductDto> searchProducts(String name, String category, BigDecimal minPrice,BigDecimal maxPrice, int page, int size, String sortBy, String direction);
}
