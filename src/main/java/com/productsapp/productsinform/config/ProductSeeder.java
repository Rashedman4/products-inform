package com.productsapp.productsinform.config;

import com.productsapp.productsinform.model.entity.ProductEntity;
import com.productsapp.productsinform.repository.ProductRepo;
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

    private final ProductRepo productRepo;

    @Override
    public void run(String... args) {
        if (productRepo.count() > 0) {
            return; // prevents duplicate insert if data already exists
        }

        List<String> categories = List.of(
                "Electronics", "Books", "Clothing", "Home", "Sports",
                "Beauty", "Toys", "Food", "Office", "Automotive"
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
        List<ProductEntity> products = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            String name = firstWords.get(random.nextInt(firstWords.size()))
                    + " "
                    + secondWords.get(random.nextInt(secondWords.size()))
                    + " "
                    + i;

            String category = categories.get(random.nextInt(categories.size()));

            BigDecimal price = BigDecimal.valueOf(5 + random.nextInt(496)); // 5 to 500
            int quantity = 1 + random.nextInt(100); // 1 to 100

            products.add(new ProductEntity(price, name, category, quantity));
        }

        productRepo.saveAll(products);

        System.out.println("Inserted 100 products successfully.");
    }
}