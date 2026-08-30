package org.example.crudexample.service;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.crudexample.dto.*;
import org.example.crudexample.entity.Product;
import org.example.crudexample.mapper.ProductMapper;
import org.example.crudexample.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Продукт с id %s не найден", id)));
        return productMapper.toResponse(product);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse createProduct(ProductCreateDto createDto) {
        Product product = productMapper.toEntity(createDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductUpdateDto updateDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Продукт с id %s не найден", id)));
        productMapper.updateEntityFromDto(updateDto, product);
        return productMapper.toResponse(productRepository.save(product));
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (productRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(String.format("Продукт с id %s не найден", id));
        }
        productRepository.delete(id);
    }

    @Transactional
    public List<ProductResponse> saveAllProducts(List<ProductCreateDto> dto) {
        List<Product> products = productMapper.toEntityList(dto);
        List<Product> savedProducts = productRepository.saveAll(products);
        return productMapper.toResponseList(savedProducts);
    }

    @Transactional
    public ProductQuantityResponse getQuantitiesByProductName(ProductNameRequest request) {
        List<String> names = request.getNames();
        if (names == null || names.isEmpty()) {
            return new ProductQuantityResponse(Collections.emptyMap());
        }
        List<Product> productsName = productRepository.findByNameIn(names);
        Map<String, Integer> foundMap = productsName.stream()
                .collect(Collectors.toMap(
                        Product::getName,
                        Product::getStockQuantity,
                        (existingProduct, replacement) -> (Integer) replacement
                ));
        Map<String, Long> result = new LinkedHashMap<>();
        for (String name : names) {
            Integer quantity = foundMap.getOrDefault(name, 0);
            result.put(name, Long.valueOf(quantity));
        }
        return new ProductQuantityResponse(result);
    }
}
