package be.one16.barka.klant.port.in.klant;

import org.springframework.transaction.annotation.Transactional;

public interface UpdateKlantUnitOfWork {

    @Transactional
    void updateKlant(UpdateKlantCommand updateKlantCommand);

}
