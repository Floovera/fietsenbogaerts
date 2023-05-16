package be.one16.barka.magazijn.ports.in;

import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface DeleteArtikelLeverancierInMagazijnUnitOfWork {

    @Transactional
    void deleteArtikelLeverancierInMagazijn(DeleteArtikelLeverancierCommand deleteArtikelLeverancierCommand);

}
