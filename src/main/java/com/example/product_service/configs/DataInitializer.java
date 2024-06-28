package com.example.product_service.configs;

import com.example.product_service.model.Product;
import com.example.product_service.repo.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository) {
        return args -> {
            Product product1 = new Product();
            product1.setCategory("Toys");
            product1.setName("Product1");
            product1.setDescription("Description for product 1");
            product1.setPrice(10.99);
            product1.setQuantity(100);

            Product product2 = new Product();
            product2.setCategory("Candies");
            product2.setName("Product2");
            product2.setDescription("Description for product 2");
            product2.setPrice(20.99);
            product2.setQuantity(150);

            productRepository.save(product1);
            productRepository.save(product2);

            System.out.println("Initialized database with products");
        };
    }
}

