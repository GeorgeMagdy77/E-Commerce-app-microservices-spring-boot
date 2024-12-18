package com.example.customerservice.services;


import com.example.customerservice.dto.CustomerRequest;
import com.example.customerservice.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {
    public List<CustomerResponse> getAll();

    public CustomerResponse getById(String Id);

    public CustomerResponse save(CustomerRequest CustomerRequest);

    public void delete(String Id);

    public CustomerResponse update(String customerId , CustomerRequest CustomerRequest);


}
