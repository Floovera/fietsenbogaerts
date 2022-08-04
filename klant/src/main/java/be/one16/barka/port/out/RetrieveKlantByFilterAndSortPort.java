package be.one16.barka.port.out;

import be.one16.barka.domain.Klant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RetrieveKlantByFilterAndSortPort {

    Page<Klant> retrieveKlantByFilterAndSort(String naam, Pageable pageable);

}
