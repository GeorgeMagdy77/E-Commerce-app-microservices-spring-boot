package com.example.orderservice.dto;

public record OrderLineResponse(
        Integer id,
        double quantity
) { }