package com.dp.vstore_productservice.controllerAdvices;

import com.dp.vstore_productservice.exceptions.ProductAlreadyPresentException;
import com.dp.vstore_productservice.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<Map<String, String>> handle(String message, HttpStatus status) {
        Map<String, String> map = new HashMap<>();
        map.put("message", message);
        return new ResponseEntity<>(map, status);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFoundException(ProductNotFoundException ex) {
        return handle(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductAlreadyPresentException.class)
    public ResponseEntity<Map<String, String>> handleProductAlreadyPresentException(ProductAlreadyPresentException ex) {
        return handle(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        return handle(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
