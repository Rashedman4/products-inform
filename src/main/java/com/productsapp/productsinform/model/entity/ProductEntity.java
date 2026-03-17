package com.productsapp.productsinform.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductEntity extends BaseEntity{


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "price_id", nullable = false, unique = true)
    private MoneyEntity price;

    @Column(nullable = false, length = 55)
    private String name;
    @Column(nullable = false, length = 55)
    private String category;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private boolean isSeen;
    @Column(nullable = false)
    private boolean isPaid;
    public ProductEntity(BigDecimal price, String name, String category, int quantity){
        setPrice(price);
        setName(name);
        setCategory(category);
        setQuantity(quantity);
        setPaid(false);
        setSeen(false);
    }

    public MoneyEntity getMoney(){return this.price;}
    public void setPrice(BigDecimal price) {
        if(price.doubleValue()<0)
            throw new IllegalArgumentException("price cant be negative");
        if(this.price==null){
            this.price=new MoneyEntity(price);
        }
        this.price.setAmount(price);
    }
    public void setName(String name) {
        if(name==null || name.trim().isEmpty())
            throw new IllegalArgumentException("name cant be null");
        this.name = name;
    }
    public void setCategory(String category) {
        if(category==null || category.trim().isEmpty()){
            throw new IllegalArgumentException("Categroy cant be empty!");
        }
        this.category=category.trim();

    }
    public void setQuantity(int quantity) {
        if(quantity<0)
            throw new IllegalArgumentException("quantity cant be negative");
        this.quantity = quantity;
    }
    public void setSeen(boolean seen){
        isSeen=seen;
    }

    public void setPaid(boolean paid){
        isPaid=paid;
    }
}
