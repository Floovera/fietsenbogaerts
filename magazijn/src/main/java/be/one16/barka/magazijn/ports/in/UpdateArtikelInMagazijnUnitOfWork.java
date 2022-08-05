package be.one16.barka.magazijn.ports.in;

import org.springframework.transaction.annotation.Transactional;

public interface UpdateArtikelInMagazijnUnitOfWork {

    @Transactional
    void updateArtikelInMagazijn(UpdateArtikelCommand updateArtikelCommand);
}
