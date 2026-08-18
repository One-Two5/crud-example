package org.example.crudexample.service;
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
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product createProduct(Product product) {
        product.setId(null);
        return productRepository.save(product);
    }

    @Transactional
    public void updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setQuantity(product.getQuantity());
        }
        productRepository.update(existingProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.delete(id);
    }
}
