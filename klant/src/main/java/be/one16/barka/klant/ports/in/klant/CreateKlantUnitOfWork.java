package be.one16.barka.klant.ports.in.klant;

import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CreateKlantUnitOfWork {

    @Transactional
    UUID createKlant(CreateKlantCommand createKlantCommand);

}
