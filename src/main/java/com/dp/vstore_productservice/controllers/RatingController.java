package com.dp.vstore_productservice.controllers;

import com.dp.vstore_productservice.dtos.RatingDto;
import com.dp.vstore_productservice.exceptions.ProductNotFoundException;
import com.dp.vstore_productservice.exceptions.RatingIsAddedException;
import com.dp.vstore_productservice.exceptions.RatingNotFoundException;
import com.dp.vstore_productservice.services.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products/rate")
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    //todo: Show the aggregated average of all ratings for a product when showing a product.

    @PostMapping("/{id}/{productId}")
    public ResponseEntity<RatingDto> rate(@PathVariable Long id,
                                          @PathVariable Long productId,
                                          @RequestParam(name = "rating") Double rating) throws ProductNotFoundException, RatingIsAddedException {
        return new ResponseEntity<>(
                RatingDto.from(ratingService.rate(id, productId, rating)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}/{productId}")
    public ResponseEntity<RatingDto> updateRating(@PathVariable Long id,
                                                  @PathVariable Long productId,
                                                  @RequestParam(name = "rating") Double rating)
            throws RatingNotFoundException, ProductNotFoundException {
        return new ResponseEntity<>(
                RatingDto.from(ratingService.updateRating(id, productId, rating)),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRating(@PathVariable Long id) throws RatingNotFoundException {
        return new ResponseEntity<>(
                ratingService.deleteRating(id),
                HttpStatus.OK
        );
    }
}
