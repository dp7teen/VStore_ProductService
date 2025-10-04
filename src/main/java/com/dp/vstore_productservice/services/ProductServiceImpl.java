package com.dp.vstore_productservice.services;

import com.dp.vstore_productservice.dtos.CreateProductDto;
import com.dp.vstore_productservice.dtos.ProductDto;
import com.dp.vstore_productservice.dtos.SearchProductsDto;
import com.dp.vstore_productservice.dtos.UpdateProductDto;
import com.dp.vstore_productservice.exceptions.ProductAlreadyPresentException;
import com.dp.vstore_productservice.exceptions.ProductNotFoundException;
import com.dp.vstore_productservice.models.Category;
import com.dp.vstore_productservice.models.Product;
import com.dp.vstore_productservice.repositories.CategoryRepository;
import com.dp.vstore_productservice.repositories.ProductRepository;
import com.dp.vstore_productservice.services.productUpdater.*;
import com.dp.vstore_productservice.strategies.productSearchingStrategy.SearchByCategory;
import com.dp.vstore_productservice.strategies.productSearchingStrategy.SearchByProduct;
import com.dp.vstore_productservice.strategies.productSearchingStrategy.SearchingStrategy;
import com.dp.vstore_productservice.utils.SortingHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private List<ProductUpdater> productUpdaters;
    private List<SearchingStrategy> searchingStrategies;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    private Optional<Product> findProductByName(String name) {
        return productRepository.findByProductName(name);
    }

    @Override
    public Product addProduct(CreateProductDto dto) throws ProductAlreadyPresentException {
        Optional<Product> optionalProduct = productRepository.findByProductName(dto.getProductName());
        if (optionalProduct.isPresent() && !optionalProduct.get().getDeleted()) {
            throw new ProductAlreadyPresentException(String.format("Product '%s' is already present.", dto.getProductName()));
        }
        Product product = new  Product();
        product.setProductName(dto.getProductName());
        product.setProductDescription(dto.getProductDescription());
        product.setProductPrice(dto.getProductPrice());
        product.setCategory(new ArrayList<>());

        dto.getCategory().forEach(category -> {
            Optional<Category> optionalCategory = categoryRepository.findByCategoryName(category);
            if (optionalCategory.isPresent() && !optionalCategory.get().getDeleted()) {
                product.getCategory().add(optionalCategory.get());
            }
            else {
                Category newCategory = new Category();
                newCategory.setProducts(new ArrayList<>());
                newCategory.setCategoryName(category);
                product.getCategory().add(newCategory);
            }
        });

        product.setStock(dto.getStock());
        product.setRatings(new ArrayList<>());

        return productRepository.save(product);
    }

    @Override
    public String deleteProduct(Long id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findByIdAndDeletedFalse(id);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException(String.format("Product '%s' not found.", id));
        }
        Product product = optionalProduct.get();
        product.setDeleted(true);
        product.setLastModifiedAt(new Date());
        productRepository.save(product);
        return "Successfully deleted product.";
    }

    @Override
    public Product updateProduct(Long id, UpdateProductDto dto) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findByIdAndDeletedFalse(id);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException(String.format("Product '%s' not found.", id));
        }
        if (optionalProduct.get().getDeleted()) {
            throw new RuntimeException("Product is already deleted.");
        }
        Product product = optionalProduct.get();
        initiateProductUpdaters();
        for (ProductUpdater productUpdater : productUpdaters) {
            productUpdater.update(product, dto);
        }
        product.setLastModifiedAt(new Date());
        return productRepository.save(product);
    }

    @Override
    public Page<ProductDto> getProducts(SearchProductsDto dto, int page, int size) throws ProductNotFoundException {
        List<Sort.Order> sortedBy = SortingHelper.sortHelper(dto.getSortBy());
        initiateSearchingStrategies();
        for (SearchingStrategy searchingStrategy : searchingStrategies) {
            Page<ProductDto> pages = searchingStrategy.search(dto.getName(), sortedBy, page, size);
            if (pages != null) {
                return pages;
            }
        }
        throw new ProductNotFoundException(String.format("Product '%s' not found.", dto.getName()));
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findByIdAndDeletedFalse(id);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException(String.format("Product '%s' not found.", id));
        }
        return optionalProduct.get();
    }

    @Override
    public Boolean updateStock(Long id, int quantityToDeduct) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findByIdAndDeletedFalse(id);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException(String.format("Product '%s' not found.", id));
        }
        Product product = optionalProduct.get();
        product.setStock(product.getStock() - quantityToDeduct);
        productRepository.save(product);
        return true;
    }

    @Override
    public Map<String, Integer> getStock(Long id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findByIdAndDeletedFalse(id);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException(String.format("Product '%s' not found.", id));
        }
        Product product = optionalProduct.get();
        return Map.of("stock", product.getStock());
    }

    private void initiateProductUpdaters() {
        productUpdaters = List.of(
                new UpdateProductName(), new UpdateProductDesc(),
                new UpdateProductCategory(), new UpdateProductPrice(),
                new UpdateProductStock()
        );
    }

    private void initiateSearchingStrategies() {
        searchingStrategies = List.of(
                new SearchByProduct(), new SearchByCategory()
        );
    }
}
