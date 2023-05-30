package be.one16.barka.klant.core.klant;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.klant.ports.in.klant.DeleteKlantUnitOfWork;
import be.one16.barka.klant.ports.out.klant.DeleteKlantPort;

import java.util.List;
import java.util.UUID;

@UnitOfWork
public class DefaultDeleteKlantUnitOfWork implements DeleteKlantUnitOfWork {

    private final List<DeleteKlantPort> deleteKlantPorts;

    public DefaultDeleteKlantUnitOfWork(List<DeleteKlantPort> deleteKlantPorts) {
        this.deleteKlantPorts = deleteKlantPorts;
    }

    @Override
    public void deleteKlant(UUID id) {
        deleteKlantPorts.forEach(port -> port.deleteKlant(id));
    }
}
