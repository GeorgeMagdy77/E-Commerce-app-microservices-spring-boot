package com.example.customerservice.dto;

import com.example.customerservice.entities.Address;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;

public record CustomerRequest(

        String id,

        @NotEmpty
        @NotNull(message = "Customer firstname is required")
        String firstName,

        @NotEmpty
        @NotNull(message = "Customer lastname is required")
        String lastName,

        @NotEmpty
        @NotNull(message = "Customer Email is required")
        @Email(message = "Customer Email is not a valid email address")
        String email,


        Address address,

        @NotEmpty
        @NotNull(message = "Customer Age is required")
        @Positive(message = "Customer Age should be positive")
        @Min(18)
        @Max(90)
        Integer age
        )
{
}
