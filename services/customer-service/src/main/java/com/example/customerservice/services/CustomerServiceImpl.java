package com.example.customerservice.services;

import com.example.customerservice.dto.CustomerRequest;
import com.example.customerservice.dto.CustomerResponse;
import com.example.customerservice.entities.Customer;
import com.example.customerservice.exception.DuplicateResourceException;
import com.example.customerservice.exception.RequestValidationException;
import com.example.customerservice.exception.ResourceNotFoundException;
import com.example.customerservice.mapper.CustomerMapper;
import com.example.customerservice.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final CustomerMapper mapper;

    @Override
    public List<CustomerResponse> getAll() {
        List<Customer> Customers=customerRepository.findAll();
        List<CustomerResponse> list = Customers.stream().map(Customer -> mapper.fromCustomer(Customer))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public CustomerResponse getById(String Id) {

        Customer customer = customerRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException(
                "customer with id [%s] not found".formatted(Id)
        ));

        CustomerResponse customerResponse = mapper.fromCustomer(customer);
        return customerResponse;

    }

    @Override
    public CustomerResponse save(CustomerRequest customerRequest) {

        /// check if email exists ---> throw an exception
        String email = customerRequest.email();
        if(customerRepository.existsCustomerByEmail(email)){
            throw new DuplicateResourceException("email already taken");
        }
        /// add
        //Customer customer = modelMapper.map(customerRequest, Customer.class);
        Customer customer = mapper.toCustomer(customerRequest);
        customer = customerRepository.save(customer);
        //CustomerResponse customerResponse = modelMapper.map(customer,CustomerResponse.class);
        CustomerResponse customerResponse = mapper.fromCustomer(customer);
        return customerResponse;

    }

    @Override
    public void delete(String Id) {
        if (!customerRepository.existsCustomerById(Id)) {
            throw new ResourceNotFoundException(
                    "customer with id [%s] not found".formatted(Id)
            );
        }
        customerRepository.deleteById(Id);
    }


    @Override
    public CustomerResponse update(String customerId , CustomerRequest updateRequest) {

        CustomerResponse customerResponse = getById(customerId);
        Customer customer = modelMapper.map(customerResponse, Customer.class);


        boolean isChanged = false;

        if (updateRequest.firstName() != null && updateRequest.lastName() != null && !updateRequest.firstName().equals(customer.getFirstName()) && !updateRequest.lastName().equals(customer.getLastName())) {

            customer.setFirstName(updateRequest.firstName());
            customer.setLastName(updateRequest.lastName());
            isChanged = true;
        }


        if (updateRequest.email() != null && !updateRequest.email().equals(customer.getEmail())) {
            if (customerRepository.existsCustomerByEmail(updateRequest.email())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            customer.setEmail(updateRequest.email());
            isChanged = true;
        }

        if(updateRequest.age() != null && !updateRequest.age().equals(customer.getAge())){
            customer.setAge(updateRequest.age());
            isChanged = true;
        }

        if(updateRequest.address() != null && !updateRequest.address().equals(customer.getAddress())){
            customer.setAddress(updateRequest.address());
        }

        /// if data is the same as it is requested to be updated
        if (!isChanged) {
            throw new RequestValidationException("no data changes , as it remains the same");
        }


        customer = customerRepository.save(customer);
        CustomerResponse updatedcustomerResponse = modelMapper.map(customer,CustomerResponse.class);
        return updatedcustomerResponse;

    }


}
