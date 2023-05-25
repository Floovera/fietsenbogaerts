package be.one16.barka.klant.core.klant;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.klant.domain.Klant;
import be.one16.barka.klant.ports.in.klant.KlantenQuery;
import be.one16.barka.klant.ports.in.klant.RetrieveKlantFilterAndSortCommand;
import be.one16.barka.klant.ports.out.klant.LoadKlantenPort;
import org.springframework.data.domain.Page;

import java.util.UUID;

@UnitOfWork
public class DefaultKlantenQuery implements KlantenQuery {

    private final LoadKlantenPort loadKlantenPort;

    public DefaultKlantenQuery(LoadKlantenPort loadKlantenPort) {
        this.loadKlantenPort = loadKlantenPort;
    }

    @Override
    public Klant retrieveKlantById(UUID id) {
        return loadKlantenPort.retrieveKlantById(id);
    }

    @Override
    public Page<Klant> retrieveKlantenByFilterAndSort(RetrieveKlantFilterAndSortCommand retrieveKlantFilterAndSortCommand) {
        return loadKlantenPort.retrieveKlantenByFilterAndSort(retrieveKlantFilterAndSortCommand.naam(), retrieveKlantFilterAndSortCommand.pageable());
    }
}
