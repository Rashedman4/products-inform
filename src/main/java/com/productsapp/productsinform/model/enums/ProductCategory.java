package com.productsapp.productsinform.model.enums;

import com.productsapp.productsinform.error.BadRequestException;
import com.productsapp.productsinform.error.NotFoundException;

import java.util.Arrays;

public enum ProductCategory {
    ELECTRONICS,
    OFFICE,
    BOOKS,
    FASHION,
    GROCERY,
    TOYS;

    public static ProductCategory from(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new BadRequestException("Category is required");
        }

        String normalized = value.trim()
                .toUpperCase();

        return Arrays.stream(values())
                .filter(category -> category.name().equals(normalized))
                .findFirst()
                .orElseThrow(() ->
                        new BadRequestException("Invalid category: " + value));
    }
}