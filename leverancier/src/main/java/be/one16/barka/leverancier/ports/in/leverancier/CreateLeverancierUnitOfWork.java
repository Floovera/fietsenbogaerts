package be.one16.barka.leverancier.ports.in.leverancier;

import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CreateLeverancierUnitOfWork {

    @Transactional
    UUID createLeverancier(CreateLeverancierCommand createLeverancierCommand);

}
