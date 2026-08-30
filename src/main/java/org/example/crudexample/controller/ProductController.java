package org.example.crudexample.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.crudexample.dto.*;
import org.example.crudexample.entity.Product;
import org.example.crudexample.service.GenerateTokenService;
import org.example.crudexample.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final GenerateTokenService generateTokenService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAllProducts(@RequestHeader(value = "X-Auth-Token", required = true)
                                                               String token) {
        if (token.isBlank() || !generateTokenService.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ошибка получения токена, Неверный или истекший токен");
        }
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findProductById(@PathVariable Long id,
                                                   @RequestHeader(value = "X-Auth-Token") String token) {
        if (token.isBlank() || !generateTokenService.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ошибка получения токена, Неверный или истекший токен");
        }
            ProductResponse response = productService.getProductById(id);
            return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductCreateDto dto,
                                                         @RequestHeader(value = "X-Auth-Token")
                                                         String token) {
        if (token.isBlank() || !generateTokenService.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ошибка получения токена, Неверный или истекший токен");
        }
        ProductResponse response = productService.createProduct(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProductById(@PathVariable Long id,
                                                             @RequestBody ProductUpdateDto dto,
                                                             @RequestHeader(value = "X-Auth-Token") String token) {
        if (token.isBlank()|| !generateTokenService.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ошибка получения токена, Неверный или истекший токен");
        }
            ProductResponse response = productService.updateProduct(id, dto);
            return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id,
                                                  @RequestHeader(value = "X-Auth-Token") String token) {
        if (token.isBlank() || !generateTokenService.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ошибка получения токена, Неверный или истекший токен");
        }
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
    }

    @PostMapping("/batch")
    public ResponseEntity<ProductResponse> createBulkProduct(@RequestBody List<ProductCreateDto> products,
                                               @RequestHeader(value = "X-Auth-Token") String token) {
        if (token.isBlank() || !generateTokenService.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ошибка получения токена, Неверный или истекший токен");
        }

        List<ProductResponse> savedProducts = productService.saveAllProducts(products);
        return savedProducts.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().build();
    }

    @PostMapping("/quantity")
    public ResponseEntity<?> getQuantities(@RequestBody ProductNameRequest request,
                                           @RequestHeader(value = "X-Auth-Token") String token) {
        if (token.isBlank() || !generateTokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        }
        try {
            ProductQuantityResponse response = productService.getQuantitiesByProductName(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Не удалось получить список продуктов!%s".formatted(e.getMessage()));
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
