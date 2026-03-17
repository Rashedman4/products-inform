package com.productsapp.productsinform.controller;


import com.productsapp.productsinform.model.dto.ProductDto;
import com.productsapp.productsinform.model.dto.ProductRequest;
import com.productsapp.productsinform.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ProductDto addProduct(@Valid @RequestBody ProductRequest req) {
        return productService.addProduct(req);
    }
    @GetMapping("/id/{id}")
    public ProductDto getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/name/{name}")
    public List<ProductDto> getProductByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }
    @GetMapping("/category/{category}")
    public List<ProductDto> getProductByCategory(@PathVariable String category) {
        return productService.getProductByCategory(category);
    }
    @GetMapping("/price/{price}")
    public List<ProductDto> getProductByPrice(@PathVariable BigDecimal price) {
        return productService.getProductByPrice(price);
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @DeleteMapping("/{id}")
    public ProductDto deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }


    @PutMapping("/{id}")
    public ProductDto updateProduct(@Valid @RequestBody ProductRequest req,@PathVariable long id) {
        return productService.updateProduct(req,id);
    }

    @GetMapping("/sort/name")
    public List<ProductDto> sortByName() {
        return productService.sortByName();
    }
    @GetMapping("/sort/price")
    public List<ProductDto> sortByPrice() {
        return productService.sortByPrice();
    }
    @GetMapping("/sort/category")
    public List<ProductDto> sortByCetegory() {
        return productService.sortByCetegory();
    }
    @GetMapping("/sort/id")
    public List<ProductDto> sortById(){
        return productService.sortById();
    }

}
