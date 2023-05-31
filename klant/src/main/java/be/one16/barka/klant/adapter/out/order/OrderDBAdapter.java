package be.one16.barka.klant.adapter.out.order;

import be.one16.barka.domain.exceptions.EntityNotFoundException;
import be.one16.barka.klant.adapter.mapper.order.OrderJpaEntityMapper;
import be.one16.barka.klant.adapter.out.repository.OrderRepository;
import be.one16.barka.klant.domain.Order;
import be.one16.barka.klant.ports.out.order.CreateOrderPort;
import be.one16.barka.klant.ports.out.order.DeleteOrderPort;
import be.one16.barka.klant.ports.out.order.LoadOrdersPort;
import be.one16.barka.klant.ports.out.order.UpdateOrderPort;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@org.springframework.core.annotation.Order(Ordered.HIGHEST_PRECEDENCE)
public class OrderDBAdapter implements LoadOrdersPort, CreateOrderPort, UpdateOrderPort, DeleteOrderPort {

    private final OrderRepository orderRepository;

    private final OrderJpaEntityMapper orderJpaEntityMapper;

    public OrderDBAdapter(OrderRepository orderRepository, OrderJpaEntityMapper orderJpaEntityMapper) {
        this.orderRepository = orderRepository;
        this.orderJpaEntityMapper = orderJpaEntityMapper;
    }

    @Override
    public Order retrieveOrderById(UUID id) {
        OrderJpaEntity orderJpaEntity = getOrderJpaEntityById(id);

        return orderJpaEntityMapper.mapJpaEntityToOrder(orderJpaEntity);
    }

    @Override
    public Page<Order> retrieveOrdersByFilterAndSort(String naam, Pageable pageable) {
        Specification<OrderJpaEntity> specification = Specification.where(naam == null ? null : (Specification<OrderJpaEntity>) ((root, query, builder) -> builder.like(root.get("naam"), "%" + naam + "%")));
        return orderRepository.findAll(specification, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()))
                .map(orderJpaEntityMapper::mapJpaEntityToOrder);
    }

    @Override
    public void createOrder(Order order) {
        OrderJpaEntity orderJpaEntity = new OrderJpaEntity();

        orderJpaEntity.setUuid(order.getOrderId());
        orderJpaEntity.setOrderType(order.getOrderType());
        orderJpaEntity.setNaam(order.getNaam());
        orderJpaEntity.setOpmerkingen(order.getOpmerkingen());
        orderJpaEntity.setDatum(order.getDatum());
        orderJpaEntity.setKlantId(order.getKlantId());
        orderJpaEntity.setReparatieNummer(order.getReparatieNummer());
        orderJpaEntity.setOrderNummer(order.getOrderNummer());
        orderRepository.save(orderJpaEntity);
    }

    @Override
    public void updateOrder(Order order) {
        OrderJpaEntity orderJpaEntity = getOrderJpaEntityById(order.getOrderId());
        orderJpaEntity.setOrderType(order.getOrderType());
        orderJpaEntity.setNaam(order.getNaam());
        orderJpaEntity.setOpmerkingen(order.getOpmerkingen());
        orderJpaEntity.setDatum(order.getDatum());
        orderJpaEntity.setKlantId(order.getKlantId());
        orderJpaEntity.setReparatieNummer(order.getReparatieNummer());
        orderJpaEntity.setOrderNummer(order.getOrderNummer());

        orderRepository.save(orderJpaEntity);
    }

    @Override
    public void deleteOrder(UUID id) {
        OrderJpaEntity orderJpaEntity = getOrderJpaEntityById(id);
        orderRepository.delete(orderJpaEntity);
    }

    private OrderJpaEntity getOrderJpaEntityById(UUID id) {
        return orderRepository.findByUuid(id).orElseThrow(() -> new EntityNotFoundException(String.format("Order with uuid %s doesn't exist", id)));
    }


}
