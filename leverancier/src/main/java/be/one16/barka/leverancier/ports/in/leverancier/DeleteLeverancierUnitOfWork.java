package be.one16.barka.leverancier.ports.in.leverancier;

import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface DeleteLeverancierUnitOfWork {

    @Transactional
    void deleteLeverancier(UUID id);

}
