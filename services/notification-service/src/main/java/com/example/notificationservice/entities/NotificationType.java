package com.example.notificationservice.entities;


// This enum is used to differentiate the type of notification whether
// it belongs to the order or to the payment
public enum NotificationType {
    ORDER_CONFIRMATION,
    PAYMENT_CONFIRMATION
}