package be.one16.barka.klant.port.out.verkoop;

import be.one16.barka.klant.domain.Verkoop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface LoadVerkopenPort {

    Page<Verkoop> retrieveVerkopenByFilterAndSort(String naam, Pageable pageable);

    Verkoop retrieveVerkoopById(UUID id);


}
