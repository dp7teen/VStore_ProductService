package com.dp.vstore_productservice.repositories;

import com.dp.vstore_productservice.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long> {
    @Override
    Optional<Rating> findById(Long ratingId);

    Double getAverageRatingByProduct_Id(Long productId);

    Optional<Rating> findByIdAndDeletedFalse(Long productId);

    Optional<Rating> findByUserIdAndDeletedFalse(Long userId);
}
