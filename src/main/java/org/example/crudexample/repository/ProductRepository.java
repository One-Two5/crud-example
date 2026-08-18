package org.example.crudexample.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.crudexample.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<Product> findById(Long id) {
        Product product = entityManager.find(Product.class, id);
        return Optional.ofNullable(product);
    }

    public List<Product> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);
        criteriaQuery.select(productRoot);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public Product save(Product product) {
        if (product.getId() != null) {
            return entityManager.merge(product);
        } else {
            entityManager.persist(product);
            return product;
        }
    }

    public void delete(Long id) {
        Product product = entityManager.find(Product.class, id);
        if (product != null) {
            entityManager.remove(product);
        }
    }
}
