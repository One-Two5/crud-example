package org.example.crudexample.repository;

import org.example.crudexample.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository {

    Product findById(Long id);
    List<Product> findAll();
    Product save(Product product);
    Product updateProduct(Product product);
    void deleteById(Long id);

}
