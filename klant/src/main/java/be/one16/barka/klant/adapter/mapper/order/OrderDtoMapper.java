package be.one16.barka.klant.adapter.mapper.order;


import be.one16.barka.klant.adapter.in.order.OrderDto;
import be.one16.barka.klant.domain.Order;
import be.one16.barka.klant.ports.in.order.CreateOrderCommand;
import be.one16.barka.klant.ports.in.order.UpdateOrderCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OrderDtoMapper {

    CreateOrderCommand mapDtoToCreateOrderCommand(OrderDto order);
    @Mapping(source = "orderId", target = "orderId")
    UpdateOrderCommand mapDtoToUpdateOrderCommand(OrderDto order, UUID orderId);

    OrderDto mapOrderToDto(Order order);

}
