package be.one16.barka.klant.core.verkoop;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.klant.ports.in.verkoop.DeleteVerkoopUnitOfWork;
import be.one16.barka.klant.ports.out.verkoop.DeleteVerkoopPort;

import java.util.List;
import java.util.UUID;

@UnitOfWork
public class DefaultDeleteVerkoopUnitOfWork implements DeleteVerkoopUnitOfWork {

    private final List<DeleteVerkoopPort> deleteVerkoopPorts;

    public DefaultDeleteVerkoopUnitOfWork(List<DeleteVerkoopPort> deleteVerkoopPorts) {
        this.deleteVerkoopPorts = deleteVerkoopPorts;
    }

    @Override
    public void deleteVerkoop(UUID id) {
        deleteVerkoopPorts.forEach(port -> port.deleteVerkoop(id));
    }
}
