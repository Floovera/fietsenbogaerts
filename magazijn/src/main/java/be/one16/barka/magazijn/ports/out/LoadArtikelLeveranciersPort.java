package be.one16.barka.magazijn.ports.out;

import be.one16.barka.magazijn.domain.Artikel;
import be.one16.barka.magazijn.domain.Leverancier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface LoadArtikelLeveranciersPort {

  Optional<Leverancier> retrieveArtikelLeverancierById(UUID id);

}
