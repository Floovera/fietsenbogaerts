package be.one16.barka.klant.core.verkoop;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.klant.domain.Verkoop;
import be.one16.barka.klant.ports.in.verkoop.RetrieveVerkoopFilterAndSortCommand;
import be.one16.barka.klant.ports.in.verkoop.VerkopenQuery;
import be.one16.barka.klant.ports.out.verkoop.LoadVerkopenPort;
import org.springframework.data.domain.Page;

import java.util.UUID;

@UnitOfWork
public class DefaultVerkopenQuery implements VerkopenQuery {

    private final LoadVerkopenPort loadVerkopenPort;

    public DefaultVerkopenQuery(LoadVerkopenPort loadVerkopenPort) {
        this.loadVerkopenPort = loadVerkopenPort;
    }

    @Override
    public Verkoop retrieveVerkoopById(UUID id) {
        return loadVerkopenPort.retrieveVerkoopById(id);
    }

    @Override
    public Page<Verkoop> retrieveVerkopenByFilterAndSort(RetrieveVerkoopFilterAndSortCommand retrieveVerkoopFilterAndSortCommand) {
        return loadVerkopenPort.retrieveVerkopenByFilterAndSort(retrieveVerkoopFilterAndSortCommand.naam(), retrieveVerkoopFilterAndSortCommand.pageable());
    }
}
