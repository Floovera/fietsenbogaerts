package be.one16.barka.klant.ports.in.order;

import be.one16.barka.klant.common.exceptions.KlantNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public interface UpdateOrderUnitOfWork {

    @Transactional
    void updateOrder(UpdateOrderCommand updateOrderCommand) throws KlantNotFoundException;

}
