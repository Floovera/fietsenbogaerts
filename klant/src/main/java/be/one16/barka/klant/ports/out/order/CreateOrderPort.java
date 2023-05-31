package be.one16.barka.klant.ports.out.order;

import be.one16.barka.klant.domain.Order;

public interface CreateOrderPort {

    void createOrder(Order order);

}
