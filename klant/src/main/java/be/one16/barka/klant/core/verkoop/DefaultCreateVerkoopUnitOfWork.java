package be.one16.barka.klant.core.verkoop;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.domain.exceptions.EntityNotFoundException;
import be.one16.barka.klant.common.exceptions.KlantNotFoundException;
import be.one16.barka.klant.domain.Klant;
import be.one16.barka.klant.domain.Verkoop;
import be.one16.barka.klant.ports.in.klant.KlantenQuery;
import be.one16.barka.klant.ports.in.verkoop.CreateVerkoopCommand;
import be.one16.barka.klant.ports.in.verkoop.CreateVerkoopUnitOfWork;
import be.one16.barka.klant.ports.out.verkoop.CreateVerkoopPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@UnitOfWork
public class DefaultCreateVerkoopUnitOfWork implements CreateVerkoopUnitOfWork {

    private final List<CreateVerkoopPort> createVerkoopPorts;

    private final KlantenQuery klantenquery;

    public DefaultCreateVerkoopUnitOfWork(List<CreateVerkoopPort> createVerkoopPorts, KlantenQuery klantenquery) {
        this.createVerkoopPorts = createVerkoopPorts;
        this.klantenquery = klantenquery;
    }

    @Override
    @Transactional
    public UUID createVerkoop(CreateVerkoopCommand createVerkoopCommand) throws KlantNotFoundException {
        UUID calculatedKlantId = null;

        if (createVerkoopCommand.klantId() != null) {
            try {
                Klant klantRetrieved = getKlant(createVerkoopCommand.klantId());
                calculatedKlantId = klantRetrieved.getKlantId();
            } catch (EntityNotFoundException e) {
                throw new KlantNotFoundException(String.format("Did not find the klant for the provided UUID: %s", createVerkoopCommand.klantId()));
            }
        }

        Verkoop verkoop = Verkoop.builder()
                .verkoopId(UUID.randomUUID())
                .naam(createVerkoopCommand.naam())
                .opmerkingen(createVerkoopCommand.opmerkingen())
                .klantId(calculatedKlantId)
                .build();

        createVerkoopPorts.forEach(port -> port.createVerkoop(verkoop));

        return verkoop.getVerkoopId();
    }

    private Klant getKlant(UUID klantID) {
        return klantenquery.retrieveKlantById(klantID);
    }
}
