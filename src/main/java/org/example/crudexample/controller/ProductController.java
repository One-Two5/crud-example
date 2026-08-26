package org.example.crudexample.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.example.crudexample.dto.ProductDto;
import org.example.crudexample.service.GenerateTokenService;
import org.example.crudexample.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> findAllProducts(@RequestHeader(value = "X-Auth-Token", required = true) String token) {
        if (token == null || !generateTokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        }
        List<ProductDto> productsDto = productService.getAllProducts();
        return ResponseEntity.ok(productsDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findProductById(@PathVariable Long id,
                                                   @RequestHeader(value = "X-Auth-Token") String token) {
        if (token.isBlank() || !generateTokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        }
        try {
            ProductDto productDto = productService.getProductById(id);
            return ResponseEntity.ok(productDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDto,
                                           @RequestHeader(value = "X-Auth-Token")
                                           String token) {
        if (token.isBlank() || !generateTokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        }
        if (productDto == null) {
            throw new EntityNotFoundException("Тело запроса не может быть пустым");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductById(@PathVariable Long id, @RequestBody ProductDto productDto,
                                                     @RequestHeader(value = "X-Auth-Token") String token) {
        if (token.isBlank()|| !generateTokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        }
        try {
            ProductDto updateProductDto = productService.updateProduct(id, productDto);
            return ResponseEntity.ok(updateProductDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id,
                                                  @RequestHeader(value = "X-Auth-Token") String token) {
        if (token.isBlank() || !generateTokenService.validateToken(token)) {
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
    public ResponseEntity<?> createBulkProduct(@RequestBody List<ProductDto> productDto,
                                               @RequestHeader(value = "X-Auth-Token") String token) {
        if (token.isBlank() || !generateTokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        }
        try {
            productService.saveAllProducts(productDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/quantity")
    public ResponseEntity<?> getQuantities(@RequestBody List<String> names,
                                             @RequestHeader(value = "X-Auth-Token") String token) {
        if (token.isBlank() || !generateTokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        }
        try {
            Map<String, String> quantities = productService.getQuantitiesByProductName(names);
            return ResponseEntity.ok(quantities);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось получить список продуктов!");
        }
    }
}
