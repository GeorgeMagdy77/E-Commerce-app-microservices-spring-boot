package com.example.orderservice.external.client;


import com.example.orderservice.external.response.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


@FeignClient(
        name = "customer-service",
        url = "${application.config.customer-url}"  // fetch the customer url property from order-service.yml file
)
public interface CustomerClient {

    @GetMapping("/{id}")
    Optional<CustomerResponse> getById(@PathVariable("id") String id);
}