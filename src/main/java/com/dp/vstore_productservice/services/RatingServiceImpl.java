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
import java.util.List;
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
    public Rating rate(Long userId, Long productId, double rating) throws ProductNotFoundException, RatingIsAddedException {
        Optional<Product> optionalProduct = productRepository.findByIdAndDeletedFalse(productId);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product not found.");
        }
        Product product = optionalProduct.get();

        Optional<Rating> optionalRating = ratingRepository.findByUserIdAndProduct_IdAndDeletedFalse(userId, productId);
        if (optionalRating.isPresent()) {
            throw new RatingIsAddedException("Your rating is added already. If you want you can update!");
        }
        Rating ratingEntity = new Rating();
        ratingEntity.setProduct(product);
        ratingEntity.setRating(rating);
        ratingEntity.setUserId(userId);
        ratingRepository.save(ratingEntity);

        double avgRating = ratingRepository.getAverageRatingByProduct_Id(productId);
        product.setAverageRating(avgRating);
        productRepository.save(product);

        return ratingEntity;
    }

    @Override
    public String deleteRating(Long userId, Long ratingId) throws RatingNotFoundException {
        Optional<Rating> optionalRating = ratingRepository.findByIdAndDeletedFalse(ratingId);
        if (optionalRating.isEmpty()) {
            throw new RatingNotFoundException(String.format("Rating with id = %s is not found", ratingId));
        }
        if (!optionalRating.get().getUserId().equals(userId)) {
            throw new RatingNotFoundException("You cannot delete this rating. Hence you did not rate this product!");
        }
        Rating rating = optionalRating.get();
        rating.setDeleted(true);
        rating.setLastModifiedAt(new Date());
        ratingRepository.save(rating);
        return String.format("Rating with id = %s has been deleted", ratingId);
    }

    @Override
    public Rating updateRating(Long userId, Long ratingId, Long productId, double rating) throws RatingNotFoundException, ProductNotFoundException {
        Optional<Rating> optionalRating = ratingRepository.findByIdAndDeletedFalse(ratingId);
        if (optionalRating.isEmpty()) {
            throw new RatingNotFoundException(String.format("Rating with id = %s is not found", ratingId));
        }
        if (!optionalRating.get().getUserId().equals(userId)) {
            throw new RatingNotFoundException("You cannot update this rating. Hence you did not rate this product!");
        }

        Optional<Product> optionalProduct = productRepository.findByIdAndDeletedFalse(productId);

        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("You cannot rate this product. Hence product is not found or deleted!");
        }

        Rating ratingEntity = optionalRating.get();
        ratingEntity.setRating(rating);
        ratingEntity.setLastModifiedAt(new Date());
        ratingRepository.save(ratingEntity);

        Product product = optionalProduct.get();
        product.setAverageRating(ratingRepository.getAverageRatingByProduct_Id(productId));
        productRepository.save(product);

        return ratingEntity;
    }
}
