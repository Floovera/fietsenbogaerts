package be.one16.barka.port.in;

import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface DeleteKlantUnitOfWork {

    @Transactional
    void deleteKlant(UUID id);

}
