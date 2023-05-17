package be.one16.barka.magazijn.ports.in;

import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface UpdateArtikelLeverancierInMagazijnUnitOfWork {

    @Transactional
    void updateArtikelLeverancierInMagazijn(UpdateArtikelLeverancierCommand updateArtikelLeverancierCommand);

}
