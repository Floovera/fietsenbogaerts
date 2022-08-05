package be.one16.barka.klant.core;

import be.one16.barka.klant.port.in.DeleteKlantUnitOfWork;
import be.one16.barka.klant.port.out.DeleteKlantPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
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
