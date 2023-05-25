package be.one16.barka.klant.ports.in.verkoop;

import be.one16.barka.klant.domain.Verkoop;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface VerkopenQuery {

    @Transactional(readOnly = true)
    Verkoop retrieveVerkoopById(UUID id);

    @Transactional(readOnly = true)
    Page<Verkoop> retrieveVerkopenByFilterAndSort(RetrieveVerkoopFilterAndSortCommand retrieveVerkoopFilterAndSortCommand);

}
