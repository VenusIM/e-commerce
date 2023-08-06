package com.msa.orderservice.controller;

import com.msa.orderservice.Mapper.HttpMapper;
import com.msa.orderservice.Mapper.OrderMapper;
import com.msa.orderservice.dto.OrderDTO;
import com.msa.orderservice.service.OrderService;
import com.msa.orderservice.vo.RequestOrder;
import com.msa.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {

    private final Environment environment;
    private final OrderService orderService;
    private final HttpMapper httpMapper;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's working in order-service on PORT %s", environment.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId, @RequestBody RequestOrder requestOrder) {

        OrderDTO orderDTO = httpMapper.toDTO(requestOrder);
        orderDTO.setUserId(userId);
        orderDTO = orderService.createOrder(orderDTO);

        ResponseOrder responseUser = httpMapper.toResponse(orderDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId) {
        Iterable<OrderDTO> orders = orderService.getOrdersByUserId(userId);

        List<ResponseOrder> result = new ArrayList<>();
        orders.forEach(orderDTO -> result.add(httpMapper.toResponse(orderDTO)));

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
