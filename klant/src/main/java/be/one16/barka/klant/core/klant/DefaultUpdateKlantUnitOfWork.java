package be.one16.barka.klant.core.klant;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.klant.domain.Klant;
import be.one16.barka.klant.ports.in.klant.UpdateKlantCommand;
import be.one16.barka.klant.ports.in.klant.UpdateKlantUnitOfWork;
import be.one16.barka.klant.ports.out.klant.UpdateKlantPort;

import java.util.List;

import static be.one16.barka.klant.common.KlantType.ONBEKEND;

@UnitOfWork
public class DefaultUpdateKlantUnitOfWork implements UpdateKlantUnitOfWork {

    private final List<UpdateKlantPort> updateKlantPorts;

    public DefaultUpdateKlantUnitOfWork(List<UpdateKlantPort> updateKlantPorts) {
        this.updateKlantPorts = updateKlantPorts;
    }

    @Override
    public void updateKlant(UpdateKlantCommand updateKlantCommand) {

        Klant klant = Klant.builder()
                .klantId(updateKlantCommand.klantId())
                .naam(updateKlantCommand.naam())
                .klantType(updateKlantCommand.klantType() == null ? ONBEKEND : updateKlantCommand.klantType())
                .straat(updateKlantCommand.straat())
                .huisnummer(updateKlantCommand.huisnummer())
                .bus(updateKlantCommand.bus())
                .postcode(updateKlantCommand.postcode())
                .gemeente(updateKlantCommand.gemeente())
                .land(updateKlantCommand.land())
                .telefoonnummer(updateKlantCommand.telefoonnummer())
                .mobiel(updateKlantCommand.mobiel())
                .email(updateKlantCommand.email())
                .btwNummer(updateKlantCommand.btwNummer())
                .opmerkingen(updateKlantCommand.opmerkingen())
                .build();

        updateKlantPorts.forEach(port -> port.updateKlant(klant));
    }
}
