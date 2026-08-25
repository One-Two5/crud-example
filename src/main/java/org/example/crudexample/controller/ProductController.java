package org.example.crudexample.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.example.crudexample.entity.Product;
import org.example.crudexample.service.GenerateTokenService;
import org.example.crudexample.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final GenerateTokenService generateTokenService;

    public ProductController(ProductService productService, GenerateTokenService generateTokenService) {
        this.productService = productService;
        this.generateTokenService = generateTokenService;
    }

    @GetMapping
    public ResponseEntity<?> findAllProducts(@RequestHeader(value = "X-Auth-Token", required = false )
                                                 String token) {
        if (token == null || !generateTokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        }
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findProductById(@PathVariable Long id,
                                                   @RequestHeader(value = "X-Auth-Token", required = false )
                                                   String token) {
        if (token == null || !generateTokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        }
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product,
                                           @RequestHeader(value = "X-Auth-Token", required = false )
                                           String token) {
        if (token == null || !generateTokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        }
        if (product == null) {
            throw new EntityNotFoundException("Тело запроса не может быть пустым");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductById(@PathVariable Long id, @RequestBody Product product,
                                                     @RequestHeader(value = "X-Auth-Token", required = false )
                                                     String token) {
        if (token == null || !generateTokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        }
        try {
            Product updateProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(updateProduct);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id,
                                                  @RequestHeader(value = "X-Auth-Token", required = false)
                                                  String token) {
        if (token == null || !generateTokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        }
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<?> createBulkProduct(@RequestBody List<Product> products,
                                               @RequestHeader(value = "X-Auth-Token", required = false)
                                               String token) {
        if (token == null || generateTokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        }
        try {
            productService.saveAllProducts(products);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
