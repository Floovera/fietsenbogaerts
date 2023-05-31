package be.one16.barka.klant.ports.in.order;

import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface DeleteOrderUnitOfWork {

    @Transactional
    void deleteOrder(UUID id);

}
