package be.one16.barka.klant.core.werkuur;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.klant.domain.Werkuur;
import be.one16.barka.klant.ports.in.werkuur.WerkurenQuery;
import be.one16.barka.klant.ports.out.werkuur.LoadWerkurenPort;

import java.util.List;
import java.util.UUID;

@UnitOfWork
public class DefaultWerkurenQuery implements WerkurenQuery {

    private final LoadWerkurenPort loadWerkurenPort;

    public DefaultWerkurenQuery(LoadWerkurenPort loadWerkurenPort) {
        this.loadWerkurenPort = loadWerkurenPort;
    }

    @Override
    public Werkuur retrieveWerkuurById(UUID id, UUID verkoopId) {
        return loadWerkurenPort.retrieveWerkuurOfVerkoop(id, verkoopId);
    }

    @Override
    public List<Werkuur> retrieveWerkurenOfVerkoop(UUID verkoopId) {
        return loadWerkurenPort.retrieveWerkurenOfVerkoop(verkoopId);
    }

}