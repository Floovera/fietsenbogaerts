package be.one16.barka.port.in;

import be.one16.barka.domain.Klant;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface RetrieveKlantByFilterAndSortUnitOfWork {

    @Transactional(readOnly = true)
    Page<Klant> retrieveKlantByFilterAndSort(RetrieveKlantFilterAndSortCommand retrieveKlantFilterAndSortCommand);

}
