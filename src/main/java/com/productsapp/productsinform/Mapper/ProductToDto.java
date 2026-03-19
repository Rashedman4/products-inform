package com.productsapp.productsinform.Mapper;

import com.productsapp.productsinform.model.dto.ProductDto;
import com.productsapp.productsinform.model.entity.ProductEntity;
import com.productsapp.productsinform.model.entity.ProductEntityV4;
import com.productsapp.productsinform.model.enums.ProductCategory;

public class ProductToDto {
    public static ProductDto productToDto(ProductEntity prod){
        return new ProductDto(prod.getId(), prod.getName(), ProductCategory.from( prod.getCategory()), prod.getMoney().getAmount()
                ,prod.getCreatedAt(),prod.isSeen(), prod.isPaid());
    }

    public static ProductDto productToDtoV4(ProductEntityV4 prod){
        return new ProductDto(prod.getId(), prod.getName(), prod.getCategory(), prod.getMoney().getAmount()
              ,prod.getCreatedAt(),prod.isSeen(), prod.isPaid());
    }
}
