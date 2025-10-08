package com.dp.vstore_productservice.repositories;

import com.dp.vstore_productservice.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryNameAndDeletedFalse(String categoryName);

    Optional<Category> findByCategoryName(String categoryName);

    List<Category> findAllByCategoryNameContainsIgnoreCaseAndDeletedFalse(String categoryName);
}
