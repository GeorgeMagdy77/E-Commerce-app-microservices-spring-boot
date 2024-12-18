package com.example.orderservice.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {

}
