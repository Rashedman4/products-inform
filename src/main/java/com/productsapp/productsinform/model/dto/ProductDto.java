package com.productsapp.productsinform.model.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductDto(Long productId, String name, String categroy, BigDecimal amount,
                         String currency, Instant createdAt, boolean isSeen, boolean isPaid) {
}
