package be.one16.barka.leverancier.ports.in;

import be.one16.barka.leverancier.domain.Leverancier;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface LeveranciersQuery {

    @Transactional(readOnly = true)
    Leverancier retrieveLeverancierById(UUID id);

    @Transactional(readOnly = true)
    Page<Leverancier> retrieveLeverancierByFilterAndSort(RetrieveLeveranciersFilterAndSortCommand retrieveLeveranciersFilterAndSortCommand);

}
