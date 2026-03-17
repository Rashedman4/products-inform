package com.productsapp.productsinform.Mapper;

import com.productsapp.productsinform.model.dto.ProductDto;
import com.productsapp.productsinform.model.entity.ProductEntity;

public class ProductToDto {
    public static ProductDto productToDto(ProductEntity prod){
        return new ProductDto(prod.getId(), prod.getName(), prod.getCategory(), prod.getMoney().getAmount(),
                prod.getMoney().getCurrency().getCurrencyCode(),prod.getCreatedAt(),prod.isSeen(), prod.isPaid());
    }
}
