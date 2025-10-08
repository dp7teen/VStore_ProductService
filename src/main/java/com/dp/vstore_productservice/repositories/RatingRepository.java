package com.dp.vstore_productservice.repositories;

import com.dp.vstore_productservice.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long> {
    @Override
    Optional<Rating> findById(Long ratingId);

    @Query("SELECT AVG(r.rating) FROM ratings r WHERE r.product.id = :productId")
    double getAverageRatingByProduct_Id(Long productId);

    Optional<Rating> findByIdAndDeletedFalse(Long productId);

    Optional<Rating> findByUserIdAndProduct_IdAndDeletedFalse(Long userId, Long productId);
}
