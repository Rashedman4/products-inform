package com.productsapp.productsinform.service;

import com.productsapp.productsinform.Mapper.ProductToDto;
import com.productsapp.productsinform.error.NotFoundException;
import com.productsapp.productsinform.model.dto.ProductDto;
import com.productsapp.productsinform.model.dto.ProductRequest;
import com.productsapp.productsinform.model.entity.ProductEntity;
import com.productsapp.productsinform.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
@RequiredArgsConstructor
@Service
public class ProductService implements ProductServiceInterface{
    private final ProductRepo productRepo;

    @Override
    public List<ProductDto> getProductByName(String name) {
        return productRepo.findAll().stream().filter(prod->prod.getName().toLowerCase().contains(name.toLowerCase())).map(ProductToDto::productToDto).toList();
    }

    @Override
    public List<ProductDto> getProductByCategory(String category) {
        return productRepo.findAll().stream().filter(prod->prod.getCategory().toLowerCase().contains(category.toLowerCase())).map(ProductToDto::productToDto).toList();
    }

    @Override
    public List<ProductDto> getProductByPrice(BigDecimal price) {
        return productRepo.findAll().stream().filter(prod->(prod.getMoney().getAmount().doubleValue()>price.doubleValue()-2&&prod.getMoney().getAmount().doubleValue()<price.doubleValue()+2)).map(ProductToDto::productToDto).toList();
    }

    @Override
    public ProductDto addProduct(ProductRequest req) {
        ProductEntity prod =productRepo.save(new ProductEntity(req.price(), req.name(), req.category(), req.quantity()));

        return ProductToDto.productToDto(prod);
    }
    @Override
    public ProductDto getProductById(Long id) {
        ProductEntity prod=productRepo.findById(id).orElseThrow(()->new NotFoundException("Product Not Found"));
        ProductDto temp=ProductToDto.productToDto(prod);
        prod.setSeen(true);
        prod.setPaid(true);
        productRepo.save(prod);
        return temp;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepo.findAll().stream().map(ProductToDto::productToDto).toList();
    }

    @Override
    public ProductDto deleteProduct(Long id) {
        ProductEntity prod = productRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Product Id with " + id + " Not Found."));
        productRepo.deleteById(id);
        return ProductToDto.productToDto(prod);    }


    @Override
    public ProductDto updateProduct(ProductRequest req, long id) {
        ProductEntity prod = productRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Product Id with " + id + " Not Found."));
        prod.setPrice(req.price());
        prod.setCategory(req.category());
        prod.setName(req.name());
        prod.setQuantity(req.quantity());
        productRepo.save(prod);
        return ProductToDto.productToDto(prod);
    }

    @Override
    public List<ProductDto> sortByName() {
        return productRepo.findAll().stream().sorted(Comparator.comparing(ProductEntity::getName)).map(ProductToDto::productToDto).toList();
    }
    @Override
    public List<ProductDto> sortByPrice() {
        return productRepo.findAll().stream().sorted(Comparator.comparing(prod->prod.getMoney().getAmount())).map(ProductToDto::productToDto).toList();
    }
    @Override
    public List<ProductDto> sortByCetegory() {
        return productRepo.findAll().stream().sorted(Comparator.comparing(ProductEntity::getCategory)).map(ProductToDto::productToDto).toList();
    }
    @Override
    public List<ProductDto> sortById(){
        return productRepo.findAll().stream().sorted(Comparator.comparingLong(ProductEntity::getId)).map(ProductToDto::productToDto).toList();
    }

}
