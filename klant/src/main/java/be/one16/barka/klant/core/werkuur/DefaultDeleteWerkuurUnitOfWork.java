package be.one16.barka.klant.core.werkuur;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.klant.ports.in.werkuur.DeleteWerkuurCommand;
import be.one16.barka.klant.ports.in.werkuur.DeleteWerkuurUnitOfWork;
import be.one16.barka.klant.ports.out.werkuur.WerkuurDeletePort;
import java.util.List;

@UnitOfWork
public class DefaultDeleteWerkuurUnitOfWork implements DeleteWerkuurUnitOfWork {

    private final List<WerkuurDeletePort> werkuurDeletePorts;

    public DefaultDeleteWerkuurUnitOfWork(List<WerkuurDeletePort> werkuurDeletePorts) {
        this.werkuurDeletePorts = werkuurDeletePorts;
    }

    @Override
    public void deleteWerkuur(DeleteWerkuurCommand deleteWerkuurCommand) {
        werkuurDeletePorts.forEach(port -> port.deleteWerkuur(deleteWerkuurCommand.werkuurId(), deleteWerkuurCommand.orderId()));
    }

}
