package be.one16.barka.klant.ports.in.klant;

import org.springframework.transaction.annotation.Transactional;

public interface UpdateKlantUnitOfWork {

    @Transactional
    void updateKlant(UpdateKlantCommand updateKlantCommand);

}
