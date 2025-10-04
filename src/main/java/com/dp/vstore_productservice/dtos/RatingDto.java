package com.dp.vstore_productservice.dtos;

import com.dp.vstore_productservice.models.Rating;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RatingDto {
    private Long userId;
    private Long productId;
    private Double rating;

    public static RatingDto from(Rating rating) {
        RatingDto dto = new RatingDto();
        dto.userId = rating.getUserId();
        dto.productId = rating.getProduct().getId();
        dto.rating = rating.getRating();
        return dto;
    }
}
