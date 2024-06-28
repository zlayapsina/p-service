package com.example.product_service.service;

import com.example.product_service.exceptions.ResourceNotFoundException;
import com.example.product_service.model.Product;
import com.example.product_service.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) throws ResourceNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) throws ResourceNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        productRepository.delete(product);
    }

    public boolean checkProductAvailability(Long id, int requestedQuantity) throws ResourceNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        return product.getQuantity() >= requestedQuantity;
    }

    public Product changeProductQuantity(Long id, int quantityChange) throws ResourceNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        int newQuantity = product.getQuantity() + quantityChange;

        if (newQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be less than zero");
        }

        product.setQuantity(newQuantity);
        return productRepository.save(product);
    }

    public int getTotalQuantityByName(String name) {
        return productRepository.findByName(name).stream().mapToInt(Product::getQuantity).sum();
    }

    public int getTotalQuantityByCategory(String category) {
        return productRepository.findByCategory(category).stream().mapToInt(Product::getQuantity).sum();
    }
}

