package com.productsapp.productsinform.model.dto;

import com.productsapp.productsinform.model.enums.ProductCategory;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductDto(Long productId, String name, ProductCategory categroy, BigDecimal amount,
                         Instant createdAt, boolean isSeen, boolean isPaid) {
}
