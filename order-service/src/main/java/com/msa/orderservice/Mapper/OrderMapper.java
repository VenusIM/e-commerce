package com.msa.orderservice.Mapper;

import com.msa.orderservice.dto.OrderDTO;
import com.msa.orderservice.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface OrderMapper {
    OrderDTO toDTO(OrderEntity orderEntity);
    OrderEntity toEntity(OrderDTO orderDTO);
}
