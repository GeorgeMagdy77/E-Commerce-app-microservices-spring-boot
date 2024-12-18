package com.example.orderservice.external.request;

import com.example.orderservice.entitites.PaymentMethod;
import com.example.orderservice.external.response.CustomerResponse;


import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}