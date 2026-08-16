package org.example.crudexample.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.crudexample.entity.Product;
import org.example.crudexample.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> findAll() {
        return productService.findAllProducts();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) throws EntityNotFoundException {
        Product product = productService.findById(id);
        return ResponseEntity.ok().body(product);
    }

    @PostMapping("/product")
    public Product createProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) throws EntityNotFoundException {
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok().body(updatedProduct);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) throws EntityNotFoundException {
        productService.deleteProductById(id);
        return ResponseEntity.ok("Продукт " + id + " был удален");
    }
}
