package com.productsapp.productsinform.repository;

import com.productsapp.productsinform.model.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepo extends JpaRepository<ProductEntity,Long> {
    Page<ProductEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<ProductEntity> findByCategoryContainingIgnoreCase(String name, Pageable pageable);
    Page<ProductEntity> findByPrice_AmountBetween(BigDecimal min,BigDecimal max, Pageable pageable);

}
