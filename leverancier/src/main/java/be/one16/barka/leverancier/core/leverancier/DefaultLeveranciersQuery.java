package be.one16.barka.leverancier.core.leverancier;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.leverancier.domain.Leverancier;
import be.one16.barka.leverancier.ports.in.leverancier.LeveranciersQuery;
import be.one16.barka.leverancier.ports.in.leverancier.RetrieveLeveranciersFilterAndSortCommand;
import be.one16.barka.leverancier.ports.out.leverancier.LoadLeveranciersPort;
import org.springframework.data.domain.Page;

import java.util.UUID;

@UnitOfWork
public class DefaultLeveranciersQuery implements LeveranciersQuery {

    private final LoadLeveranciersPort loadLeveranciersPort;

    public DefaultLeveranciersQuery(LoadLeveranciersPort loadLeveranciersPort) {
        this.loadLeveranciersPort = loadLeveranciersPort;
    }

    @Override
    public Leverancier retrieveLeverancierById(UUID id) {
        return loadLeveranciersPort.retrieveLeverancierById(id);
    }

    @Override
    public Page<Leverancier> retrieveLeverancierByFilterAndSort(RetrieveLeveranciersFilterAndSortCommand retrieveLeveranciersFilterAndSortCommand) {
        return loadLeveranciersPort.retrieveLeverancierByFilterAndSort(retrieveLeveranciersFilterAndSortCommand.naam(), retrieveLeveranciersFilterAndSortCommand.pageable());
    }

}
