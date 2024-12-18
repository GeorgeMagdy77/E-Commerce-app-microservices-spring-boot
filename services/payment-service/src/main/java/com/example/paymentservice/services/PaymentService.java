package com.example.paymentservice.services;

import com.example.paymentservice.dto.PaymentRequest;

public interface PaymentService {
    public Integer createPayment(PaymentRequest request);
}
