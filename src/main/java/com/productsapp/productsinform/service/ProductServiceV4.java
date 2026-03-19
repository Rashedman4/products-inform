package com.productsapp.productsinform.service;

import com.productsapp.productsinform.Mapper.ProductToDto;
import com.productsapp.productsinform.error.NotFoundException;
import com.productsapp.productsinform.model.dto.PageResponse;
import com.productsapp.productsinform.model.dto.ProductDto;
import com.productsapp.productsinform.model.dto.ProductRequest;
import com.productsapp.productsinform.model.entity.ProductEntityV4;
import com.productsapp.productsinform.model.enums.ProductCategory;
import com.productsapp.productsinform.repository.ProductRepo;
import com.productsapp.productsinform.repository.ProductRepoV4;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class ProductServiceV4 implements ProductServiceInterfaceV4 {

    private final ProductRepoV4 productRepo;

    @Override
    public PageResponse<ProductDto> getProductByName(String name, int page, int size, String sortBy, String direction) {
        Pageable pageable = buildPageable(page, size, sortBy, direction);
        Page<ProductDto> products = productRepo.findProductDtoByName(name.trim(), pageable);
        return buildPageResponse(products);
    }

    @Override
    public PageResponse<ProductDto> getProductByCategory(String category, int page, int size, String sortBy, String direction) {
        Pageable pageable = buildPageable(page, size, sortBy, direction);
        ProductCategory parsedCategory = ProductCategory.from(category);
        Page<ProductDto> products = productRepo.findProductDtoByCategory(parsedCategory, pageable);
        return buildPageResponse(products);
    }

    @Override
    public PageResponse<ProductDto> getProductByPrice(BigDecimal price, int page, int size, String sortBy, String direction) {
        Pageable pageable = buildPageable(page, size, sortBy, direction);
        Page<ProductDto> products = productRepo.findProductDtoByPriceRange(
                price.subtract(BigDecimal.ONE),
                price.add(BigDecimal.ONE),
                pageable
        );
        return buildPageResponse(products);
    }

    @Override
    public ProductDto getProductById(Long id) {
        ProductDto dto = productRepo.findProductDtoById(id);
        if (dto == null) {
            throw new NotFoundException("Product Not Found");
        }
        return dto;
    }

    @Override
    public PageResponse<ProductDto> getAllProducts(int page, int size, String sortBy, String direction) {
        Pageable pageable = buildPageable(page, size, sortBy, direction);
        Page<ProductDto> products = productRepo.findAllProductDtos(pageable);
        return buildPageResponse(products);
    }

    @Transactional
    @Override
    public ProductDto addProduct(ProductRequest req) {
        ProductCategory category = ProductCategory.from(req.category());
        ProductEntityV4 saved = productRepo.save(new ProductEntityV4(req.price(), req.name(), category, req.quantity()));
        return ProductToDto.productToDtoV4(saved);
    }

    @Transactional
    @Override
    public ProductDto updateProduct(ProductRequest req, long id) {
        ProductEntityV4 prod = productRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Product Id with " + id + " Not Found."));

        ProductCategory category = ProductCategory.from(req.category());
        prod.setPrice(req.price());
        prod.setCategory(category);
        prod.setName(req.name());
        prod.setQuantity(req.quantity());

        return ProductToDto.productToDtoV4(prod);

    }

    @Transactional
    @Override
    public ProductDto deleteProduct(Long id) {
        ProductEntityV4 prod = productRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Product Id with " + id + " Not Found."));

        ProductDto dto =ProductToDto.productToDtoV4(prod);

        productRepo.delete(prod);
        return dto;
    }

    @Transactional
    @Override
    public ProductDto seenPatch(Long id){
        ProductEntityV4 prod=productRepo.findById(id).orElseThrow(()->new NotFoundException("Product Not Found"));
        prod.setSeen(true);
        return ProductToDto.productToDtoV4(prod);
    }

    @Transactional
    @Override
    public ProductDto paidPatch(Long id){
        ProductEntityV4 prod=productRepo.findById(id).orElseThrow(()->new NotFoundException("Product Not Found"));
        prod.setPaid(true);
        return ProductToDto.productToDtoV4(prod);
    }
    private PageResponse<ProductDto> buildPageResponse(Page<ProductDto> products) {
        return new PageResponse<>(
                products.getContent(),
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages(),
                products.hasNext(),
                products.hasPrevious()
        );
    }

    private Pageable buildPageable(int page, int size, String sortBy, String direction) {
        List<String> allowedSortFields = List.of("id", "name", "category", "price.amount", "createdAt");

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