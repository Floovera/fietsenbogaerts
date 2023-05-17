package be.one16.barka.klant.ports.out.klant;

import be.one16.barka.klant.domain.Klant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface LoadKlantenPort {

    Page<Klant> retrieveKlantenByFilterAndSort(String naam, Pageable pageable);

    Klant retrieveKlantById(UUID id);

}
