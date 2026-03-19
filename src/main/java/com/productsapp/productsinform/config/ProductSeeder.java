package com.productsapp.productsinform.config;

import com.productsapp.productsinform.model.entity.ProductEntity;
import com.productsapp.productsinform.model.entity.ProductEntityV4;
import com.productsapp.productsinform.model.enums.ProductCategory;
import com.productsapp.productsinform.repository.ProductRepo;
import com.productsapp.productsinform.repository.ProductRepoV4;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class ProductSeeder implements CommandLineRunner {

    private final ProductRepoV4 productRepoV4;
    private final ProductRepo productRepo;
    @Override
    public void run(String... args) {
        if (productRepo.count() > 0) {
            return;
        }

        List<ProductCategory> categories = List.of(
                ProductCategory.ELECTRONICS,
                ProductCategory.BOOKS,
                ProductCategory.FASHION,
                ProductCategory.GROCERY,
                ProductCategory.OFFICE,
                ProductCategory.TOYS
        );

        List<String> firstWords = List.of(
                "Smart", "Ultra", "Eco", "Pro", "Classic",
                "Premium", "Lite", "Max", "Elite", "Modern"
        );

        List<String> secondWords = List.of(
                "Phone", "Laptop", "Bottle", "Chair", "Desk",
                "Shoes", "Watch", "Bag", "Lamp", "Headphones",
                "Keyboard", "Mouse", "Book", "Jacket", "Gloves"
        );

        Random random = new Random();
        List<ProductEntityV4> productsV4 = new ArrayList<>();
        List<ProductEntity> productsV1=new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            String name = firstWords.get(random.nextInt(firstWords.size()))
                    + " "
                    + secondWords.get(random.nextInt(secondWords.size()))
                    + " "
                    + i;

            ProductCategory category = categories.get(random.nextInt(categories.size()));
            BigDecimal price = BigDecimal.valueOf(5 + random.nextInt(496));
            int quantity = 1 + random.nextInt(100);
            productsV1.add(new ProductEntity(price,name, category.name(), quantity));
            productsV4.add(new ProductEntityV4(price, name, category, quantity));
        }

        productRepo.saveAll(productsV1);
        productRepoV4.saveAll(productsV4);
        System.out.println("Inserted 100 v4 products successfully.");
    }
}