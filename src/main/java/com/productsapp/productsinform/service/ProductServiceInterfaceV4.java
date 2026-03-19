package com.productsapp.productsinform.service;

import com.productsapp.productsinform.model.dto.PageResponse;
import com.productsapp.productsinform.model.dto.ProductDto;
import com.productsapp.productsinform.model.dto.ProductRequest;

import java.math.BigDecimal;

public interface ProductServiceInterfaceV4 {

    ProductDto addProduct(ProductRequest req);
    ProductDto updateProduct(ProductRequest req,long id);
    ProductDto deleteProduct(Long id);
    ProductDto getProductById(Long id);

    PageResponse<ProductDto> getProductByName(String name, int page, int size, String sortBy, String direction);
    PageResponse<ProductDto> getProductByCategory(String category, int page, int size, String sortBy, String direction);
    PageResponse<ProductDto> getProductByPrice(BigDecimal price, int page, int size, String sortBy, String direction);
    PageResponse<ProductDto> getAllProducts(int page, int size, String sortBy, String direction);

    ProductDto seenPatch(Long id);
    ProductDto paidPatch(Long id);

}
