package be.one16.barka.magazijn.ports.in;

import be.one16.barka.magazijn.domain.Artikel;
import be.one16.barka.magazijn.domain.Leverancier;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface ArtikelLeveranciersInMagazijnQuery {

    @Transactional(readOnly = true)
    Optional<Leverancier> retrieveArtikelLeverancierFromMagazijnById(UUID id);

}
