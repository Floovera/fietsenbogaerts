package be.one16.barka.core;

import be.one16.barka.domain.Klant;
import be.one16.barka.port.in.RetrieveKlantByIdUnitOfWork;
import be.one16.barka.port.out.RetrieveKlantByIdPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DefaultRetrieveKlantByIdUnitOfWork implements RetrieveKlantByIdUnitOfWork {

    @Autowired
    private RetrieveKlantByIdPort retrieveKlantByIdPorts;

    @Override
    public Klant retrieveKlantById(UUID id) {
        return retrieveKlantByIdPorts.retrieveKlantById(id);
    }
}
