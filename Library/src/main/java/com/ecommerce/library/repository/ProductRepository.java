package com.ecommerce.library.repository;

import com.ecommerce.library.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.is_deleted = false and p.is_active = true")
    List<Product> getAllProduct();

    @Query("select p from Product p inner join Category c ON c.id = p.category.id" +
            " where p.category.name = ?1 and p.is_active = true and p.is_deleted = false")
    List<Product> findAllByCategory(String category);
}
