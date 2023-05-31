package be.one16.barka.klant.core.order;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.klant.ports.in.order.DeleteOrderUnitOfWork;
import be.one16.barka.klant.ports.out.order.DeleteOrderPort;

import java.util.List;
import java.util.UUID;

@UnitOfWork
public class DefaultDeleteOrderUnitOfWork implements DeleteOrderUnitOfWork {

    private final List<DeleteOrderPort> deleteOrderPorts;

    public DefaultDeleteOrderUnitOfWork(List<DeleteOrderPort> deleteOrderPorts) {
        this.deleteOrderPorts = deleteOrderPorts;
    }

    @Override
    public void deleteOrder(UUID id) {
        deleteOrderPorts.forEach(port -> port.deleteOrder(id));
    }
}
