package be.one16.barka.klant.core.order;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.klant.domain.Order;
import be.one16.barka.klant.ports.in.order.RetrieveOrderFilterAndSortCommand;
import be.one16.barka.klant.ports.in.order.OrderQuery;
import be.one16.barka.klant.ports.out.order.LoadOrdersPort;
import org.springframework.data.domain.Page;

import java.util.UUID;

@UnitOfWork
public class DefaultOrderQuery implements OrderQuery {

    private final LoadOrdersPort loadOrdersPort;

    public DefaultOrderQuery(LoadOrdersPort loadOrdersPort) {
        this.loadOrdersPort = loadOrdersPort;
    }

    @Override
    public Order retrieveOrderById(UUID id) {
        return loadOrdersPort.retrieveOrderById(id);
    }

    @Override
    public Page<Order> retrieveOrdersByFilterAndSort(RetrieveOrderFilterAndSortCommand retrieveOrderFilterAndSortCommand) {
        return loadOrdersPort.retrieveOrdersByFilterAndSort(retrieveOrderFilterAndSortCommand.naam(), retrieveOrderFilterAndSortCommand.pageable());
    }
}
