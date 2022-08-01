package be.one16.barka.magazijn.ports.in;

import java.util.UUID;

public interface CreateArtikelToMagazijnUnitOfWork {

    UUID createArtikelInMagazijn(CreateArtikelCommand createArtikelCommand);
}
