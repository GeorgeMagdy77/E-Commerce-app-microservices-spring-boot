package com.example.orderservice.external.response;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        Integer age
) {

}