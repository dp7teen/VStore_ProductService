package com.dp.vstore_productservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CreateProductDto {
    @NotBlank(message = "The product name should not be empty!")
    private String productName;
    private String productDescription;

    @NotNull(message = "The product price should not be empty! At least put 0.0 if it is free of cost :)")
    @Positive
    private double productPrice;

    @NotEmpty(message = "The Category should not be left empty!")
    private List<String> category;

    @NotNull(message = "The Stock should be mentioned at least by 0.")
    @Positive
    private Integer stock;
}
