package com.example.orderservice.services;

import com.example.orderservice.Mapper.OrderMapper;
import com.example.orderservice.dto.OrderLineRequest;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.dto.PurchaseRequest;
import com.example.orderservice.entitites.Order;
import com.example.orderservice.exception.BusinessException;
import com.example.orderservice.exception.ResourceNotFoundException;
import com.example.orderservice.external.client.CustomerClient;
import com.example.orderservice.external.client.PaymentClient;
import com.example.orderservice.external.client.ProductClient;
import com.example.orderservice.external.request.PaymentRequest;
import com.example.orderservice.kafka.OrderConfirmation;
import com.example.orderservice.kafka.OrderProducer;
import com.example.orderservice.repositories.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{


    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final CustomerClient customerClient;
    private final PaymentClient paymentClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;


    @Override
    public List<OrderResponse> getAll() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> list = orders.stream().map(order -> mapper.fromOrder(order))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public OrderResponse getById(Integer Id) {
        Order order = orderRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException(
                "Order with id [%s] not found".formatted(Id)
        ));

        OrderResponse orderResponse = mapper.fromOrder(order);
        return orderResponse;
    }

    @Override
    public Integer save(OrderRequest orderRequest) throws JsonProcessingException {

        // 1- check the customer if it exists or not  ---> we will use OpenFeign
        // 2- purchase the products --> by using the product microservice ---> we wil use RestTemplate
        // 3- persist the order object
        // 4- persist the order lines
        // 5- start payment process
        // 6- send the order confirmation to customer (sending to) ---> notification microservice (kafka) [we need to send message to our kafka broker]

        var customer = customerClient.getById(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order ---> as No customer exists with the provided ID [%s]".formatted(orderRequest.customerId())));

        var purchasedProducts = productClient.purchaseProducts(orderRequest.products());

        var order = orderRepository.save(mapper.toOrder(orderRequest));

        for (PurchaseRequest purchaseRequest : orderRequest.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        var paymentRequest = new PaymentRequest(
                orderRequest.amount(),
                orderRequest.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        orderRequest.reference(),
                        orderRequest.amount(),
                        orderRequest.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }
}
