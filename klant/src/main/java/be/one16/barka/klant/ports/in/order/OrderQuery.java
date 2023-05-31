package be.one16.barka.klant.ports.in.order;

import be.one16.barka.klant.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface OrderQuery {

    @Transactional(readOnly = true)
    Order retrieveOrderById(UUID id);

    @Transactional(readOnly = true)
    Page<Order> retrieveOrdersByFilterAndSort(RetrieveOrderFilterAndSortCommand retrieveOrderFilterAndSortCommand);

}
