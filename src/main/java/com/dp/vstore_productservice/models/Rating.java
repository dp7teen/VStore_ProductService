package com.dp.vstore_productservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "ratings")
@Getter @Setter
public class Rating extends BaseModel {
    private double rating;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Product product;
    private Long userId;
}
