package com.dp.vstore_productservice.controllers;

import com.dp.vstore_productservice.dtos.RatingDto;
import com.dp.vstore_productservice.exceptions.ProductNotFoundException;
import com.dp.vstore_productservice.exceptions.RatingIsAddedException;
import com.dp.vstore_productservice.exceptions.RatingNotFoundException;
import com.dp.vstore_productservice.security.UserPrincipal;
import com.dp.vstore_productservice.services.RatingService;
import com.dp.vstore_productservice.utils.GetUserPrincipal;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products/rate")
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    private Long getUserIdFromContext() {
        UserPrincipal details = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Long.parseLong(details.id());
    }

    @PostMapping("/{productId}")
    public ResponseEntity<RatingDto> rate(@PathVariable Long productId,
                                          @RequestParam(name = "rating") double rating) throws ProductNotFoundException, RatingIsAddedException {
        Long id = getUserIdFromContext();
        return new ResponseEntity<>(
                RatingDto.from(ratingService.rate(id, productId, rating)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{ratingIid}/{productId}")
    public ResponseEntity<RatingDto> updateRating(@PathVariable Long ratingIid,
                                                  @PathVariable Long productId,
                                                  @RequestParam(name = "rating") double rating)
            throws RatingNotFoundException, ProductNotFoundException {
        return new ResponseEntity<>(
                RatingDto.from(ratingService.updateRating(Long.valueOf(GetUserPrincipal.getUserPrincipal().id()),
                        ratingIid, productId, rating)),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{ratingIid}")
    public ResponseEntity<String> deleteRating(@PathVariable Long ratingIid) throws RatingNotFoundException {
        return new ResponseEntity<>(
                ratingService.deleteRating(Long.valueOf(GetUserPrincipal.getUserPrincipal().id()), ratingIid),
                HttpStatus.OK
        );
    }
}
