package org.example.crudexample.service;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.crudexample.dto.ProductDto;
import org.example.crudexample.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Продукт с id %s не найден", id)));
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        if (productDto.getId() != null) {
            throw new IllegalArgumentException("Продукт уже существует");
        }
        return productRepository.save(productDto);
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        if (productRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(String.format("Продукт с id %s не найден", id));
        }
        productDto.setId(id);
        return productRepository.save(productDto);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (productRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(String.format("Продукт с id %s не найден", id));
        }
        productRepository.delete(id);
    }

    @Transactional
    public void saveAllProducts(List<ProductDto> productDtos) {
        productRepository.saveAll(productDtos);
    }

    @Transactional
    public Map<String, String> getQuantitiesByProductName(List<String> names) {
        List<ProductDto> productsDto = productRepository.findByNameIn(names);
         Map<String, String> foundMap = productsDto.stream().collect(Collectors.toMap(
                ProductDto::getName,
                ProductDto::getQuantity,
                (existingProduct, replacement) -> replacement
        ));
         Map<String, String> resultMap = new LinkedHashMap<>();
         for (String name : names) {
             String quantity = foundMap.getOrDefault(name, "Продукт отсутствует");
             resultMap.put(name, quantity);
         }
         return resultMap;
    }
}
