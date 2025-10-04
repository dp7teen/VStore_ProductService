package com.dp.vstore_productservice.exceptions;

public class ProductAlreadyPresentException extends Exception{
    public ProductAlreadyPresentException(String message) {
        super(message);
    }
}
