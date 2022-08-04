package be.one16.barka.port.in;

import org.springframework.transaction.annotation.Transactional;

public interface UpdateKlantUnitOfWork {

    @Transactional
    void updateKlant(UpdateKlantCommand updateKlantCommand);

}
