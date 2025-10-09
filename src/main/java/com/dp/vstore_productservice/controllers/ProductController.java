package com.dp.vstore_productservice.controllers;

import com.dp.vstore_productservice.dtos.*;
import com.dp.vstore_productservice.exceptions.ProductAlreadyPresentException;
import com.dp.vstore_productservice.exceptions.ProductNotFoundException;
import com.dp.vstore_productservice.models.Product;
import com.dp.vstore_productservice.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody CreateProductDto dto) throws ProductAlreadyPresentException {
        Product product = productService.addProduct(dto);
        return new ResponseEntity<>(ProductDto.from(product), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) throws ProductNotFoundException {
        return new ResponseEntity<>(
                productService.deleteProduct(id),
                HttpStatus.OK
        );
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable long id,
                                                    @RequestBody UpdateProductDto dto) throws ProductNotFoundException {
        Product product = productService.updateProduct(id, dto);
        return new ResponseEntity<>(ProductDto.from(product), HttpStatus.CREATED);
    }

    @PostMapping("/search")
    public ProductPageDto searchProduct(@Valid @RequestBody SearchProductsDto dto,
                                        @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                        @RequestParam(name = "size", defaultValue = "5", required = false) int size) throws ProductNotFoundException {
        return productService.getProducts(dto, page, size);
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Long id) throws ProductNotFoundException {
        return productService.getSingleProduct(id);
    }

    @PutMapping("/update/{id}/{quantityToDeduct}")
    public ResponseEntity<Boolean> updateProduct(@PathVariable long id,
                                                 @PathVariable int quantityToDeduct) throws ProductNotFoundException {
        return new ResponseEntity<>(productService.updateStock(id, quantityToDeduct), HttpStatus.CREATED);
    }

    @GetMapping("/stock/{id}")
    public Map<String, Integer> getStock(@PathVariable Long id) throws ProductNotFoundException {
        return productService.getStock(id);
    }

}
