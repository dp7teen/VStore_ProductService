package com.dp.vstore_productservice.services;

import com.dp.vstore_productservice.dtos.CreateProductDto;
import com.dp.vstore_productservice.dtos.ProductDto;
import com.dp.vstore_productservice.dtos.SearchProductsDto;
import com.dp.vstore_productservice.dtos.UpdateProductDto;
import com.dp.vstore_productservice.exceptions.ProductAlreadyPresentException;
import com.dp.vstore_productservice.exceptions.ProductNotFoundException;
import com.dp.vstore_productservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ProductService {

    Product addProduct(CreateProductDto dto) throws ProductAlreadyPresentException;

    String deleteProduct(Long id) throws ProductNotFoundException;

    Product updateProduct(Long id, UpdateProductDto dto) throws ProductNotFoundException;

    Page<ProductDto> getProducts(SearchProductsDto dto, int  page, int size) throws ProductNotFoundException;

    ProductDto getSingleProduct(Long id) throws ProductNotFoundException;

    Boolean updateStock(Long id, int quantityToDeduct) throws ProductNotFoundException;

    Map<String, Integer> getStock(Long id) throws ProductNotFoundException;
}
