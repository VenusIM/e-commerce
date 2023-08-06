package com.msa.orderservice.service;

import com.msa.orderservice.Mapper.OrderMapper;
import com.msa.orderservice.dto.OrderDTO;
import com.msa.orderservice.entity.OrderEntity;
import com.msa.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {

        orderDTO.setOrderId(UUID.randomUUID().toString());
        orderDTO.setTotalPrice(orderDTO.getQuantity() * orderDTO.getUnitPrice());


        OrderEntity orderEntity = orderMapper.toEntity(orderDTO);

        orderEntity = orderRepository.save(orderEntity);

        return orderMapper.toDTO(orderEntity);
    }

    @Override
    public OrderDTO getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        return orderMapper.toDTO(orderEntity);
    }

    @Override
    public Iterable<OrderDTO> getOrdersByUserId(String userId) {

        Iterable<OrderEntity> orderEntities = orderRepository.findByUserId(userId);

        List<OrderDTO> result = new ArrayList<>();
        orderEntities.forEach(orderEntity -> result.add(orderMapper.toDTO(orderEntity)));

        return result;
    }
}
