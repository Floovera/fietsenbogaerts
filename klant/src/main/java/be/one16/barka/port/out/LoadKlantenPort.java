package be.one16.barka.port.out;

import be.one16.barka.domain.Klant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface LoadKlantenPort {

    Page<Klant> retrieveKlantenByFilterAndSort(String naam, Pageable pageable);

    Klant retrieveKlantById(UUID id);

}
