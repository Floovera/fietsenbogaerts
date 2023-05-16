package be.one16.barka.magazijn.ports.in;

import be.one16.barka.magazijn.domain.Artikel;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ArtikelsInMagazijnQuery {

    @Transactional(readOnly = true)
    Artikel retrieveArtikelFromMagazijnById(UUID id);

    @Transactional(readOnly = true)
    Page<Artikel> retrieveArtikelsByFilterAndSort(RetrieveArtikelFilterAndSortCommand retrieveArtikelFilterAndSortCommand);

}
