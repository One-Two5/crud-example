package org.example.crudexample.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.crudexample.entity.Product;
import org.example.crudexample.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findById(Long id) throws EntityNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Продукт не найден " + id));
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) throws EntityNotFoundException {
        Product oldProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Продукт не найден " + id));
        oldProduct.setId(product.getId());
        oldProduct.setName(product.getName());
        oldProduct.setQuantity(product.getQuantity());
        Product updatedProduct = productRepository.save(oldProduct);
        return updatedProduct;
    }

    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Продукт не найден " + id));
                productRepository.deleteById(id);
    }
}
