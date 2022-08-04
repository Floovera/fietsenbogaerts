package be.one16.barka.core;

import be.one16.barka.domain.Klant;
import be.one16.barka.port.in.RetrieveKlantByFilterAndSortUnitOfWork;
import be.one16.barka.port.in.RetrieveKlantFilterAndSortCommand;
import be.one16.barka.port.out.RetrieveKlantByFilterAndSortPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class DefaultRetrieveKlantByFilterAndSortUnitOfWork implements RetrieveKlantByFilterAndSortUnitOfWork {

    @Autowired
    private RetrieveKlantByFilterAndSortPort retrieveKlantByFilterAndSortPort;

    @Override
    public Page<Klant> retrieveKlantByFilterAndSort(RetrieveKlantFilterAndSortCommand retrieveKlantFilterAndSortCommand) {
        return retrieveKlantByFilterAndSortPort.retrieveKlantByFilterAndSort(retrieveKlantFilterAndSortCommand.naam(), retrieveKlantFilterAndSortCommand.pageable());
    }
}
