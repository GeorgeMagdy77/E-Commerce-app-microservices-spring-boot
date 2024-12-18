package com.example.customerservice.dto;

import com.example.customerservice.entities.Address;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address,
        Integer age
) {
}
