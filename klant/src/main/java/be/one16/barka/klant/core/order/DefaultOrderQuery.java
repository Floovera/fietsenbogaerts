package be.one16.barka.klant.core.order;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.klant.domain.Materiaal;
import be.one16.barka.klant.domain.Order;
import be.one16.barka.klant.domain.Werkuur;
import be.one16.barka.klant.ports.in.materiaal.MaterialenQuery;
import be.one16.barka.klant.ports.in.order.RetrieveOrderFilterAndSortCommand;
import be.one16.barka.klant.ports.in.order.OrderQuery;
import be.one16.barka.klant.ports.in.werkuur.WerkurenQuery;
import be.one16.barka.klant.ports.out.order.LoadOrdersPort;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

@UnitOfWork
public class DefaultOrderQuery implements OrderQuery {

    private final LoadOrdersPort loadOrdersPort;
    private final MaterialenQuery materialenQuery;
    private final WerkurenQuery werkurenQuery;

    public DefaultOrderQuery(LoadOrdersPort loadOrdersPort, MaterialenQuery materialenQuery, WerkurenQuery werkurenQuery) {
        this.loadOrdersPort = loadOrdersPort;
        this.materialenQuery = materialenQuery;
        this.werkurenQuery = werkurenQuery;
    }

    @Override
    public Order retrieveOrderById(UUID id) {
        Order order = loadOrdersPort.retrieveOrderById(id);
        return populateMaterialenWerkuren(order);
    }

    @Override
    public Page<Order> retrieveOrdersByFilterAndSort(RetrieveOrderFilterAndSortCommand retrieveOrderFilterAndSortCommand) {
        Page<Order> orders = loadOrdersPort.retrieveOrdersByFilterAndSort(retrieveOrderFilterAndSortCommand.naam(), retrieveOrderFilterAndSortCommand.pageable());
        return orders.map(this::populateMaterialenWerkuren);
    }

    private Order populateMaterialenWerkuren(Order order) {
        UUID id = order.getOrderId();
        List<Materiaal> materialen = materialenQuery.retrieveMaterialenOfOrder(id);
        List<Werkuur> werkuren = werkurenQuery.retrieveWerkurenOfOrder(id);
        order.setMaterialen(materialen);
        order.setWerkuren(werkuren);

        return order;
    }



}
