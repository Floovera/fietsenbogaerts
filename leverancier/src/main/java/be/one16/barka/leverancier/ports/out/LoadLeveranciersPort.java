package be.one16.barka.leverancier.ports.out;

import be.one16.barka.leverancier.domain.Leverancier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface LoadLeveranciersPort {

    Leverancier retrieveLeverancierById(UUID id);

    Page<Leverancier> retrieveLeverancierByFilterAndSort(String naam, Pageable pageable);

}
