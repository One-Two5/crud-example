package org.example.crudexample.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.crudexample.dto.*;
import org.example.crudexample.service.GenerateTokenService;
import org.example.crudexample.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Продукты", description = "Методы для работы с продуктами")
public class ProductController {

    private final ProductService productService;
    private final GenerateTokenService generateTokenService;

    @Operation(
            summary = "Получить список всех продуктов",
            description = "Возвращает полную информацию о продуктах"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список продуктов",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Ошибка получения продуктов",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAllProducts(@RequestHeader(value = "X-Auth-Token", required = true)
                                                               String token) {
        if (token.isBlank() || !generateTokenService.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ошибка получения токена, Неверный или истекший токен");
        }

        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @Operation(
            summary = "Получить продукт по id",
            description = "Возвращает полную информацию о продукте на основе его уникального идентификатора"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Продукт успешно найден",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Продукт с таким ID не найден",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findProductById(@PathVariable Long id,
                                                   @RequestHeader(value = "X-Auth-Token") String token) {
        if (token.isBlank() || !generateTokenService.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ошибка получения токена, Неверный или истекший токен");
        }

            ProductResponse response = productService.getProductById(id);
            return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Создать продукт",
            description = "Создает продукт на основании входных параметров"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Продукт успешно создан",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Не удалось добавить продукт",
                    content = @Content
            )
    })
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

    @Operation(
            summary = "Изменить продукт по id",
            description = "Возвращает измененную информацию о продукте на основе его ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Продукт успешно изменен",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Продукт с таким ID не найден",
                    content = @Content
            )
    })
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

    @Operation(
            summary = "Удалить продукт по id",
            description = "Удаляет продукт из базы на основании его ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Продукт успешно удален",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Продукт с таким ID не найден",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id,
                                                  @RequestHeader(value = "X-Auth-Token") String token) {
        if (token.isBlank() || !generateTokenService.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ошибка получения токена, Неверный или истекший токен");
        }
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Отправить список продуктов",
            description = "Отправляет список товаров в CRUD сервис")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список продуктов успешно отправлен в сервис CRUD-EXAMPLE",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибка отправки батча",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/batch")
    public ResponseEntity<List<ProductButchResponse>> createBulkProduct(@RequestBody List<ProductCreateDto> request,
                                               @RequestHeader(value = "X-Auth-Token") String token) {
        if (token.isBlank() || !generateTokenService.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ошибка получения токена, Неверный или истекший токен");
        }

        List<ProductResponse> savedProducts = productService.saveAllProducts(request);
        return savedProducts.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().build();
    }

    @Operation(summary = "Получение колличества товаров по наименованию",
            description = "Отправляетс список наименований продукта в CRUD сервис и возвращает количество продуктов")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",description = "Товары на складе",
                    content = @Content(schema = @Schema(implementation = ProductQuantityResponse.class))
            ),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/quantity")
    public ResponseEntity<ProductQuantityResponse> getQuantities(@RequestBody ProductNameRequest request,
                                           @RequestHeader(value = "X-Auth-Token") String token) {
        if (token.isBlank() || !generateTokenService.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ошибка получения токена, Неверный или истекший токен");
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
