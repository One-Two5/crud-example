package org.example.crudexample.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.crudexample.entity.Product;
import org.example.crudexample.repository.ProductRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Transactional
public class ProductService implements ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Product findById(Long id) {
        return entityManager.find(Product.class, id);
    }

    @Override
    public List<Product> findAll() {
        return entityManager.createQuery("from Product", Product.class)
                .getResultList();
    }

    @Override
    public Product save(Product product) {
        entityManager.persist(product);
        return product;
    }

    @Override
    public Product updateProduct(Product product) throws EntityNotFoundException {
        Product existingProduct = entityManager.find(Product.class, product.getId());

        if (existingProduct == null) {
            throw new EntityNotFoundException(product.getName().formatted("Продукт не найде "));
        }
        existingProduct.setName(product.getName());
        existingProduct.setQuantity(product.getQuantity());
        return existingProduct;
    }

    @Override
    public void deleteById(Long id) {
        entityManager.createQuery("delete from Product p where p.id = :id")
                .setParameter("id", id).executeUpdate();
    }
}
