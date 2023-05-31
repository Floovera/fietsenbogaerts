package be.one16.barka.klant.adapter.out.order;

import be.one16.barka.domain.events.order.OrderCreatedEvent;
import be.one16.barka.domain.events.order.OrderDeletedEvent;
import be.one16.barka.domain.events.order.OrderUpdatedEvent;
import be.one16.barka.klant.domain.Order;
import be.one16.barka.klant.ports.out.order.CreateOrderPort;
import be.one16.barka.klant.ports.out.order.DeleteOrderPort;
import be.one16.barka.klant.ports.out.order.UpdateOrderPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@org.springframework.core.annotation.Order
public class OrderBroadCaster implements CreateOrderPort, UpdateOrderPort, DeleteOrderPort {

    private final ApplicationEventPublisher applicationEventPublisher;

    public OrderBroadCaster(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void createOrder(Order order) {
        applicationEventPublisher.publishEvent(new OrderCreatedEvent(order.getOrderId(), order.getNaam()));
    }

    @Override
    public void updateOrder(Order order) {
        applicationEventPublisher.publishEvent(new OrderUpdatedEvent(order.getOrderId(), order.getNaam()));
    }

    @Override
    public void deleteOrder(UUID id) {
        applicationEventPublisher.publishEvent(new OrderDeletedEvent(id));
    }
}
