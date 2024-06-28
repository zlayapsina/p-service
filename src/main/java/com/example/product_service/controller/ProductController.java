package com.example.product_service.controller;


import com.example.product_service.exceptions.ResourceNotFoundException;
import com.example.product_service.model.Product;
import com.example.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) throws ResourceNotFoundException {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) throws ResourceNotFoundException {
        Product updatedProduct = productService.updateProduct(id, productDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) throws ResourceNotFoundException {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<Boolean> checkProductAvailability(@PathVariable Long id, @RequestParam int requestedQuantity) throws ResourceNotFoundException {
        boolean available = productService.checkProductAvailability(id, requestedQuantity);
        return ResponseEntity.ok(available);
    }

    @PostMapping("/{id}/change-quantity")
    public ResponseEntity<Product> changeProductQuantity(@PathVariable Long id, @RequestParam int quantityChange) throws ResourceNotFoundException {
        Product updatedProduct = productService.changeProductQuantity(id, quantityChange);
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping("/total-quantity-by-name")
    public ResponseEntity<Integer> getTotalQuantityByName(@RequestParam("name") String name) {
        int totalQuantity = productService.getTotalQuantityByName(name);
        return ResponseEntity.ok(totalQuantity);
    }

    @GetMapping("/total-quantity-by-category")
    public ResponseEntity<Integer> getTotalQuantityByCategory(@RequestParam("category") String category) {
        int totalQuantity = productService.getTotalQuantityByCategory(category);
        return ResponseEntity.ok(totalQuantity);
    }

}

