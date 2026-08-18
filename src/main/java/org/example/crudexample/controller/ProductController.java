package org.example.crudexample.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.crudexample.entity.Product;
import org.example.crudexample.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> findAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product findProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/new")
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProductById(@PathVariable Long id, @RequestBody Product product) throws EntityNotFoundException {
        if (product.getId() == null) {
            productService.createProduct(product);
        } else {
            productService.updateProduct(id, product);
        }
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) throws EntityNotFoundException {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
