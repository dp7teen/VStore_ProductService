package com.dp.vstore_productservice.services;

import com.dp.vstore_productservice.exceptions.ProductNotFoundException;
import com.dp.vstore_productservice.exceptions.RatingIsAddedException;
import com.dp.vstore_productservice.exceptions.RatingNotFoundException;
import com.dp.vstore_productservice.models.Rating;
import org.springframework.stereotype.Service;

@Service
public interface RatingService {
    Rating rate(Long id, Long productId, Double rating) throws ProductNotFoundException, RatingIsAddedException;

    String deleteRating(Long ratingId) throws RatingNotFoundException;

    Rating updateRating(Long id, Long productId, Double rating) throws RatingNotFoundException, ProductNotFoundException;
}
