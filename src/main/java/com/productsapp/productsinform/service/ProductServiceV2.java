package com.productsapp.productsinform.service;

import com.productsapp.productsinform.Mapper.ProductToDto;
import com.productsapp.productsinform.error.NotFoundException;
import com.productsapp.productsinform.model.dto.PageResponse;
import com.productsapp.productsinform.model.dto.ProductDto;
import com.productsapp.productsinform.model.dto.ProductRequest;
import com.productsapp.productsinform.model.entity.ProductEntity;
import com.productsapp.productsinform.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@RequiredArgsConstructor
@Service
public class ProductServiceV2 implements ProductServiceInterfaceV2{
    private final ProductRepo productRepo;

    @Override
    public PageResponse<ProductDto> getProductByName(String name,int page, int size, String sortBy, String direction) {
        Pageable pageable=buildPageable(page,size,sortBy,direction);
        Page<ProductEntity> products = productRepo.findByNameContainingIgnoreCase(name.trim(), pageable);
        List<ProductDto> prodsDto=products.getContent().stream().map(ProductToDto::productToDto).toList();

        products.getContent().forEach(prod->{
            prod.setSeen(true);
            productRepo.save(prod);
        });
        return new PageResponse<>(prodsDto,
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages(),
                products.hasNext(),
                products.hasPrevious());
    }

    @Override
    public PageResponse<ProductDto> getProductByCategory(String category,int page, int size, String sortBy, String direction) {
        Pageable pageable=buildPageable(page,size,sortBy,direction);
        Page<ProductEntity> products = productRepo.findByCategoryContainingIgnoreCase(category.trim(),pageable);
        List<ProductDto> prodsDto=products.getContent().stream().map(ProductToDto::productToDto).toList();
        products.getContent().forEach(prod->{
            prod.setSeen(true);
            productRepo.save(prod);
        });
        return new PageResponse<>(prodsDto,
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages(),
                products.hasNext(),
                products.hasPrevious());

    }

    @Override
    public PageResponse<ProductDto> getProductByPrice(BigDecimal price,int page, int size, String sortBy, String direction) {
        Pageable pageable=buildPageable(page,size,sortBy,direction);
        Page<ProductEntity> products = productRepo.findByPrice_AmountBetween(price.subtract(BigDecimal.valueOf(1)),price.add(BigDecimal.valueOf(1)),pageable);
        List<ProductDto> prodsDto=products.getContent().stream().map(ProductToDto::productToDto).toList();
        products.getContent().forEach(prod->{
            prod.setSeen(true);
            productRepo.save(prod);
        });
        return new PageResponse<>(prodsDto,
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages(),
                products.hasNext(),
                products.hasPrevious());

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
    public PageResponse<ProductDto> getAllProducts(int page, int size, String sortBy, String direction) {
        Pageable pageable=buildPageable(page,size,sortBy,direction);
        Page <ProductEntity> products= productRepo.findAll(pageable);
        List<ProductDto> prodDto=products.getContent().stream().map(ProductToDto::productToDto).toList();
        return new PageResponse<>(prodDto,
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages(),
                products.hasNext(),
                products.hasPrevious());
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



    private Pageable buildPageable(int page, int size, String sortBy, String direction) {
        List<String> allowedSortFields = List.of("id", "name", "category", "price.amount");

        if (!allowedSortFields.contains(sortBy)) {
            sortBy = "id";
        }

        if (size > 50) {
            size = 50;
        }

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        return PageRequest.of(page, size, sort);
    }
}
