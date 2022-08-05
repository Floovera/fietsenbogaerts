package be.one16.barka.magazijn.ports.in;

import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface DeleteArtikelFromMagazijnUnitOfWork {

    @Transactional
    void deleteArtikelFromMagazijn(UUID id);

}
