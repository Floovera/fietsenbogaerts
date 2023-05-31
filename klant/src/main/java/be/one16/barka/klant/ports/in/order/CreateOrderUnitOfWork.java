package be.one16.barka.klant.ports.in.order;

import be.one16.barka.klant.common.exceptions.KlantNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CreateOrderUnitOfWork {

    @Transactional
    UUID createOrder(CreateOrderCommand createOrderCommand) throws KlantNotFoundException;

}
