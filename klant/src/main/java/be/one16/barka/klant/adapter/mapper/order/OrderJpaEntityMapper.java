package be.one16.barka.klant.adapter.mapper.order;

import be.one16.barka.klant.adapter.out.order.OrderJpaEntity;
import be.one16.barka.klant.domain.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderJpaEntityMapper {

    @Mapping(source = "uuid", target = "orderId")
    Order mapJpaEntityToOrder(OrderJpaEntity orderJpaEntity);

}
