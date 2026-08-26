package org.example.crudexample.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.crudexample.dto.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<ProductDto> findById(Long id) {
        ProductDto productDto = entityManager.find(ProductDto.class, id);
        return Optional.ofNullable(productDto);
    }

    public List<ProductDto> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductDto> criteriaQuery = criteriaBuilder.createQuery(ProductDto.class);
        Root<ProductDto> productRoot = criteriaQuery.from(ProductDto.class);
        criteriaQuery.select(productRoot);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public ProductDto save(ProductDto productDto) {
        if (productDto.getId() != null) {
            return entityManager.merge(productDto);
        } else {
            entityManager.persist(productDto);
            return productDto;
        }
    }

    public void delete(Long id) {
        ProductDto productDto = entityManager.find(ProductDto.class, id);
        if (productDto != null) {
            entityManager.remove(productDto);
        }
    }

    public void saveAll(List<ProductDto> productsDto) {
        for (ProductDto productDto : productsDto) {
            entityManager.persist(productDto);
        }
        ResponseEntity.ok().body(productsDto.iterator().next());
    }

    public List<ProductDto> findByNameIn(List<String> names) {
        if (names == null || names.isEmpty()) {
            throw new IllegalArgumentException("Список имен не должен быть пустым");
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductDto> criteriaQuery = criteriaBuilder.createQuery(ProductDto.class);
        Root<ProductDto> productRoot = criteriaQuery.from(ProductDto.class);
        Predicate inPredicate = productRoot.get("name").in(names);
        criteriaQuery.select(productRoot).where(inPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
