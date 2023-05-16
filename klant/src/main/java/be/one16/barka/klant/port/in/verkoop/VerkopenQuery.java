package be.one16.barka.klant.port.in.verkoop;

import be.one16.barka.klant.domain.Klant;
import be.one16.barka.klant.domain.Verkoop;
import be.one16.barka.klant.port.in.klant.RetrieveKlantFilterAndSortCommand;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface VerkopenQuery {

    @Transactional(readOnly = true)
    Verkoop retrieveVerkoopById(UUID id);

    @Transactional(readOnly = true)
    Page<Verkoop> retrieveVerkopenByFilterAndSort(RetrieveVerkoopFilterAndSortCommand retrieveVerkoopFilterAndSortCommand);

}
