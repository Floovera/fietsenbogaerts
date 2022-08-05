package be.one16.barka.port.in;

import be.one16.barka.domain.Klant;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface KlantenQuery {

    @Transactional(readOnly = true)
    Klant retrieveKlantById(UUID id);

    @Transactional(readOnly = true)
    Page<Klant> retrieveKlantenByFilterAndSort(RetrieveKlantFilterAndSortCommand retrieveKlantFilterAndSortCommand);

}
