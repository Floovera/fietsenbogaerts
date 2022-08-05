package be.one16.barka.core;

import be.one16.barka.domain.Klant;
import be.one16.barka.port.in.KlantenQuery;
import be.one16.barka.port.in.RetrieveKlantFilterAndSortCommand;
import be.one16.barka.port.out.LoadKlantenPort;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
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
