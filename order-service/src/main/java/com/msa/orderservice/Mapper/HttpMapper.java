package com.msa.orderservice.Mapper;

import com.msa.orderservice.dto.OrderDTO;
import com.msa.orderservice.vo.RequestOrder;
import com.msa.orderservice.vo.ResponseOrder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface HttpMapper {
    OrderDTO toDTO(RequestOrder requestOrder);
    ResponseOrder toResponse(OrderDTO orderDTO);
}
