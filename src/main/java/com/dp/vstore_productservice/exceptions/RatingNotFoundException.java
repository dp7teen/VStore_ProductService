package com.dp.vstore_productservice.exceptions;

import com.dp.vstore_productservice.models.Rating;

public class RatingNotFoundException extends Exception {
    public RatingNotFoundException(String message) {
        super(message);
    }
}
