package com.dp.vstore_productservice.services;

import com.dp.vstore_productservice.exceptions.ProductNotFoundException;
import com.dp.vstore_productservice.exceptions.RatingIsAddedException;
import com.dp.vstore_productservice.exceptions.RatingNotFoundException;
import com.dp.vstore_productservice.models.Product;
import com.dp.vstore_productservice.models.Rating;
import com.dp.vstore_productservice.repositories.ProductRepository;
import com.dp.vstore_productservice.repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final ProductRepository productRepository;

    public RatingServiceImpl(RatingRepository ratingRepository,
                             ProductRepository productRepository) {
        this.ratingRepository = ratingRepository;
        this.productRepository = productRepository;
    }

    private Optional<Rating> findRatingById(Long ratingId) {
        return ratingRepository.findById(ratingId);
    }

    @Override
    public Rating rate(Long userId, Long productId, Double rating) throws ProductNotFoundException, RatingIsAddedException {
        Optional<Product> optionalProduct = productRepository.findByIdAndDeletedFalse(productId);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product not found.");
        }
        Product product = optionalProduct.get();

        Optional<Rating> optionalRating = ratingRepository.findByUserIdAndDeletedFalse(userId);
        if (optionalRating.isPresent()) {
            throw new RatingIsAddedException("Your rating is added already. If you want you can update!");
        }
        Rating ratingEntity = new Rating();
        ratingEntity.setProduct(product);
        ratingEntity.setRating(rating);
        ratingEntity.setUserId(userId);

        double avgRating = ratingRepository.getAverageRatingByProduct_Id(productId);
        product.setAverageRating(avgRating);
        productRepository.save(product);

        return ratingRepository.save(ratingEntity);
    }

    @Override
    public String deleteRating(Long ratingId) throws RatingNotFoundException {
        Optional<Rating> optionalRating = ratingRepository.findByIdAndDeletedFalse(ratingId);
        if (optionalRating.isEmpty()) {
            throw new RatingNotFoundException(String.format("Rating with id = %s is not found", ratingId));
        }
        Rating rating = optionalRating.get();
        rating.setDeleted(true);
        rating.setLastModifiedAt(new Date());
        ratingRepository.save(rating);
        return String.format("Rating with id = %s has been deleted", ratingId);
    }

    @Override
    public Rating updateRating(Long ratingId, Long productId, Double rating) throws RatingNotFoundException, ProductNotFoundException {
        Optional<Rating> optionalRating = ratingRepository.findByIdAndDeletedFalse(ratingId);
        if (optionalRating.isEmpty()) {
            throw new RatingNotFoundException(String.format("Rating with id = %s is not found", ratingId));
        }

        Optional<Product> optionalProduct = productRepository.findByIdAndDeletedFalse(productId);

        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("You cannot rate this product. Hence product is not found or deleted!");
        }

        Rating ratingEntity = optionalRating.get();
        ratingEntity.setRating(rating);
        ratingEntity.setLastModifiedAt(new Date());

        Product product = optionalProduct.get();
        product.setAverageRating(ratingRepository.getAverageRatingByProduct_Id(productId));

        return ratingRepository.save(ratingEntity);
    }
}
