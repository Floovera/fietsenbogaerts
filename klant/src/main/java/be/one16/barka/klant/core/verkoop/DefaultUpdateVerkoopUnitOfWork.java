package be.one16.barka.klant.core.verkoop;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.domain.exceptions.EntityNotFoundException;
import be.one16.barka.klant.common.exceptions.KlantNotFoundException;
import be.one16.barka.klant.domain.Klant;
import be.one16.barka.klant.domain.Verkoop;
import be.one16.barka.klant.port.in.klant.KlantenQuery;
import be.one16.barka.klant.port.in.verkoop.UpdateVerkoopCommand;
import be.one16.barka.klant.port.in.verkoop.UpdateVerkoopUnitOfWork;
import be.one16.barka.klant.port.out.verkoop.UpdateVerkoopPort;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@UnitOfWork
public class DefaultUpdateVerkoopUnitOfWork implements UpdateVerkoopUnitOfWork {

    private final List<UpdateVerkoopPort> updateVerkoopPorts;
    private final KlantenQuery klantenquery;

    public DefaultUpdateVerkoopUnitOfWork(List<UpdateVerkoopPort> updateVerkoopPorts, KlantenQuery klantenquery) {
        this.updateVerkoopPorts = updateVerkoopPorts;
        this.klantenquery = klantenquery;
    }

    @Override
    @Transactional
    public void updateVerkoop(UpdateVerkoopCommand updateVerkoopCommand) throws KlantNotFoundException {

        UUID calculatedKlantId = null;

        if (updateVerkoopCommand.klantId() != null) {
            try {
                Klant klantRetrieved = getKlant(updateVerkoopCommand.klantId());
                calculatedKlantId = klantRetrieved.getKlantId();
            } catch (EntityNotFoundException e) {
                throw new KlantNotFoundException(String.format("Did not find the klant for the provided UUID: %s", updateVerkoopCommand.klantId()));
            }
        }

        Verkoop verkoop = Verkoop.builder()
                .verkoopId(updateVerkoopCommand.verkoopId())
                .orderType(updateVerkoopCommand.orderType())
                .naam(updateVerkoopCommand.naam())
                .opmerkingen(updateVerkoopCommand.opmerkingen())
                .datum(updateVerkoopCommand.datum())
                .klantId(calculatedKlantId)
                .reparatieNummer(updateVerkoopCommand.reparatieNummer())
                .orderNummer(updateVerkoopCommand.orderNummer())
                .build();

        updateVerkoopPorts.forEach(port -> port.updateVerkoop(verkoop));

    }

    private Klant getKlant(UUID klantID) {
        return klantenquery.retrieveKlantById(klantID);
    }
}
