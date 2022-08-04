package be.one16.barka.port.in;

import be.one16.barka.domain.Klant;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface RetrieveKlantByIdUnitOfWork {

    @Transactional(readOnly = true)
    Klant retrieveKlantById(UUID id);
}
