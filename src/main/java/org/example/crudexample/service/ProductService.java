package org.example.crudexample.service;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.crudexample.entity.Product;
import org.example.crudexample.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Продукт с id %s не найден", id)));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product createProduct(Product product) {
        if (product.getId() != null) {
            throw new IllegalArgumentException("Продукт уже существует");
        }
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, Product product) {
        if (productRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(String.format("Продукт с id %s не найден", id));
        }
        product.setId(id);
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (productRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(String.format("Продукт с id %s не найден", id));
        }
        productRepository.delete(id);
    }
}
