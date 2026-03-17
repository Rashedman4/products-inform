package com.productsapp.productsinform.service;

import com.productsapp.productsinform.model.dto.ProductDto;
import com.productsapp.productsinform.model.dto.ProductRequest;

import java.math.BigDecimal;
import java.util.List;

public interface ProductServiceInterface {

    ProductDto addProduct(ProductRequest req);
    ProductDto updateProduct(ProductRequest req,long id);
    ProductDto deleteProduct(Long id);
    ProductDto getProductById(Long id);
    List<ProductDto> getProductByName(String name);
    List<ProductDto> getProductByCategory(String category);
    List<ProductDto> getProductByPrice(BigDecimal price);


    List<ProductDto> sortById();
    List<ProductDto> sortByName();
    List<ProductDto> sortByCetegory();
    List<ProductDto> sortByPrice();
    List<ProductDto> getAllProducts();



}
