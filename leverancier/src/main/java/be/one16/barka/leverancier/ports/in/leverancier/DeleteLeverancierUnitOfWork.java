package be.one16.barka.leverancier.ports.in.leverancier;

import be.one16.barka.leverancier.domain.Leverancier;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface DeleteLeverancierUnitOfWork {

    @Transactional
    void deleteLeverancier(Leverancier leverancier);

}
