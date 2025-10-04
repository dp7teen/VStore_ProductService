package com.dp.vstore_productservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UpdateProductDto {
    private String productName;
    private String productDescription;
    private double productPrice;
    private List<String> category;
    private Integer stock;
}
