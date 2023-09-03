package com.ecommerce.library.repository;

import com.ecommerce.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "update Category set name = ?1 where id = ?2")
    Category update(String name, Long id);
}
