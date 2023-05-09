package be.one16.barka.klant.port.in.klant;

import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface DeleteKlantUnitOfWork {

    @Transactional
    void deleteKlant(UUID id);

}
