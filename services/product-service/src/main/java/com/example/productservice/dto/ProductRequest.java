package com.example.productservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(

        Integer id,

        @NotNull(message = "Product name is required")
        String name,

        @NotNull(message = "Product description is required")
        String description,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Available quantity should be positive")
        double availableQuantity,

        @NotNull(message = "Description is required")
        @Positive(message = "Price should be positive")
        BigDecimal price,


        @NotNull(message = "Product category is required")
        Integer categoryId
) {
}
