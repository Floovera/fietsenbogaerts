package be.one16.barka.leverancier.ports.in;

import org.springframework.transaction.annotation.Transactional;

public interface UpdateLeverancierUnitOfWork {

    @Transactional
    void updateLeverancier(UpdateLeverancierCommand updateLeverancierCommand);

}
