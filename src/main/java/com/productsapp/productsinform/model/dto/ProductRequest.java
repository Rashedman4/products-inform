package com.productsapp.productsinform.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(
        @NotNull BigDecimal price, @NotEmpty String name,@NotEmpty String category, @Min(1) int quantity) {
}
