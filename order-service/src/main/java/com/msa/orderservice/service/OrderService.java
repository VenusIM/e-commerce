package com.msa.orderservice.service;

import com.msa.orderservice.dto.OrderDTO;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO getOrderByOrderId(String orderId);
    Iterable<OrderDTO> getOrdersByUserId(String userId);
}
