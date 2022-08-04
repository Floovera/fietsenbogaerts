package be.one16.barka.core;

import be.one16.barka.port.in.DeleteKlantUnitOfWork;
import be.one16.barka.port.out.DeleteKlantPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultDeleteKlantUnitOfWork implements DeleteKlantUnitOfWork {

    @Autowired
    private List<DeleteKlantPort> deleteKlantPorts;

    @Override
    public void deleteKlant(UUID id) {
        deleteKlantPorts.forEach(port -> port.deleteKlant(id));
    }
}
