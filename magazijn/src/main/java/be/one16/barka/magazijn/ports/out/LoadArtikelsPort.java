package be.one16.barka.magazijn.ports.out;

import be.one16.barka.magazijn.domain.Artikel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface LoadArtikelsPort {

    Artikel retrieveArtikelById(UUID id);

    Page<Artikel> retrieveArtikelByFilterAndSort(String code, String merk, String omschrijving, UUID leverancierId, Pageable pageable);

}
