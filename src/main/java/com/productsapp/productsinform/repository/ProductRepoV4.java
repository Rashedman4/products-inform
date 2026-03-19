package com.productsapp.productsinform.repository;

import com.productsapp.productsinform.model.dto.ProductDto;
import com.productsapp.productsinform.model.entity.ProductEntityV4;
import com.productsapp.productsinform.model.enums.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepoV4 extends JpaRepository<ProductEntityV4, Long> {

    @Query("""
        SELECT new com.productsapp.productsinform.model.dto.ProductDto(
            p.id,
            p.name,
            p.category,
            p.price.amount,
            p.createdAt,
            p.isSeen,
            p.isPaid
        )
        FROM ProductEntityV4 p
        WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))
        """)
    Page<ProductDto> findProductDtoByName(@Param("name") String name, Pageable pageable);

    @Query("""
        SELECT new com.productsapp.productsinform.model.dto.ProductDto(
            p.id,
            p.name,
            p.category,
            p.price.amount,
            p.createdAt,
            p.isSeen,
            p.isPaid
        )
        FROM ProductEntityV4 p
        WHERE p.category = :category
        """)
    Page<ProductDto> findProductDtoByCategory(@Param("category") ProductCategory category, Pageable pageable);

    @Query("""
        SELECT new com.productsapp.productsinform.model.dto.ProductDto(
            p.id,
            p.name,
            p.category,
            p.price.amount,
            p.createdAt,
            p.isSeen,
            p.isPaid
        )
        FROM ProductEntityV4 p
        WHERE p.price.amount BETWEEN :min AND :max
        """)
    Page<ProductDto> findProductDtoByPriceRange(@Param("min") BigDecimal min,
                                                @Param("max") BigDecimal max,
                                                Pageable pageable);

    @Query("""
        SELECT new com.productsapp.productsinform.model.dto.ProductDto(
            p.id,
            p.name,
            p.category,
            p.price.amount,
            p.createdAt,
            p.isSeen,
            p.isPaid
        )
        FROM ProductEntityV4 p
        """)
    Page<ProductDto> findAllProductDtos(Pageable pageable);

    @Query("""
        SELECT new com.productsapp.productsinform.model.dto.ProductDto(
            p.id,
            p.name,
            p.category,
            p.price.amount,
            p.createdAt,
            p.isSeen,
            p.isPaid
        )
        FROM ProductEntityV4 p
        WHERE p.id = :id
        """)
    ProductDto findProductDtoById(@Param("id") Long id);


}