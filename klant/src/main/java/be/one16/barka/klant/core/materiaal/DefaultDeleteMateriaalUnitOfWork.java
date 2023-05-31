package be.one16.barka.klant.core.materiaal;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.klant.ports.in.materiaal.DeleteMateriaalCommand;
import be.one16.barka.klant.ports.in.materiaal.DeleteMateriaalUnitOfWork;
import be.one16.barka.klant.ports.out.materiaal.MateriaalDeletePort;
import java.util.List;

@UnitOfWork
public class DefaultDeleteMateriaalUnitOfWork implements DeleteMateriaalUnitOfWork {

    private final List<MateriaalDeletePort> materiaalDeletePorts;

    public DefaultDeleteMateriaalUnitOfWork(List<MateriaalDeletePort> materiaalDeletePorts) {
        this.materiaalDeletePorts = materiaalDeletePorts;
    }

    @Override
    public void deleteMateriaal(DeleteMateriaalCommand deleteMateriaalCommand) {
        materiaalDeletePorts.forEach(port -> port.deleteMateriaal(deleteMateriaalCommand.materiaalId(), deleteMateriaalCommand.orderId()));
    }

}
