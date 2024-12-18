package com.example.customerservice.entities;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    private String id;

    @NotEmpty
    @NotNull(message = "Customer firstname is required")
    private String firstName;

    @NotEmpty
    @NotNull(message = "Customer lastname is required")
    private String lastName;

    @Size(min = 10,max = 50)
    @NotEmpty
    @NotNull(message = "Customer Email is required")
    @Email(message = "Customer Email is not a valid email address")
    private String email;

    private Address address;

    @NotEmpty
    @NotNull(message = "Customer Age is required")
    @Positive(message = "Customer Age should be positive")
    @Min(18)
    @Max(90)
    private Integer age;

}
