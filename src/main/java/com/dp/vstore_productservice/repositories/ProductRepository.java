package com.dp.vstore_productservice.repositories;

import com.dp.vstore_productservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductName(String productName);

    List<Product> findAllByProductNameContainingIgnoreCaseAndDeletedFalse(String productName, Pageable pageable);

    Optional<Product> findByIdAndDeletedFalse(Long id);
}
