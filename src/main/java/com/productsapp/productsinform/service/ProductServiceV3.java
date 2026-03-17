package com.productsapp.productsinform.service;

import com.productsapp.productsinform.Mapper.ProductToDto;
import com.productsapp.productsinform.model.dto.PageResponse;
import com.productsapp.productsinform.model.dto.ProductDto;
import com.productsapp.productsinform.model.entity.ProductEntity;
import com.productsapp.productsinform.repository.ProductRepoV3;
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
public class ProductServiceV3 implements ProductServiceInterfaceV3{
    private final ProductRepoV3 productRepoV3;

    @Override
    public PageResponse<ProductDto> searchProducts(String name, String category, BigDecimal minPrice,BigDecimal maxPrice, int page, int size, String sortBy, String direction) {
        if (page < 0) {
            throw new IllegalArgumentException("page cannot be negative");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("size must be greater than 0");
        }

        if (size > 50) {
            size = 50;
        }

        if (minPrice != null && minPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("minPrice cannot be negative");
        }

        if (maxPrice != null && maxPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("maxPrice cannot be negative");
        }

        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) > 0) {
            throw new IllegalArgumentException("minPrice cannot be greater than maxPrice");
        }
        Pageable pageable=buildPageable(page, size, sortBy, direction);
        Page<ProductEntity> result = productRepoV3.searchProducts(
                normalize(name),
                normalize(category),
                minPrice,
                maxPrice,
                pageable
        );

        return new PageResponse<>(result.getContent().stream().map(ProductToDto::productToDto).toList(), result.getNumber(), result.getSize(), result.getNumberOfElements(), result.getTotalPages(), result.hasNext(), result.hasPrevious());

    }

    private Pageable buildPageable(int page, int size, String sortBy, String direction) {
        List<String> allowedSortFields = List.of("id", "name", "category", "createdAt", "price.amount");

        if (!allowedSortFields.contains(sortBy)) {
            sortBy = "id";
        }

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        return PageRequest.of(page, size, sort);
    }

    private String normalize(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }
}
