package be.one16.barka.klant.core;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.klant.domain.Klant;
import be.one16.barka.klant.port.in.CreateKlantCommand;
import be.one16.barka.klant.port.in.CreateKlantUnitOfWork;
import be.one16.barka.klant.port.out.CreateKlantPort;

import java.util.List;
import java.util.UUID;

import static be.one16.barka.klant.common.KlantType.ONBEKEND;

@UnitOfWork
public class DefaultCreateKlantUnitOfWork implements CreateKlantUnitOfWork {

    private final List<CreateKlantPort> createKlantPorts;

    public DefaultCreateKlantUnitOfWork(List<CreateKlantPort> createKlantPorts) {
        this.createKlantPorts = createKlantPorts;
    }

    @Override
    public UUID createKlant(CreateKlantCommand createKlantCommand) {
        Klant klant = Klant.builder()
                .klantId(UUID.randomUUID())
                .naam(createKlantCommand.naam())
                .klantType(createKlantCommand.klantType() == null ? ONBEKEND : createKlantCommand.klantType())
                .straat(createKlantCommand.straat())
                .huisnummer(createKlantCommand.huisnummer())
                .bus(createKlantCommand.bus())
                .postcode(createKlantCommand.postcode())
                .gemeente(createKlantCommand.gemeente())
                .land(createKlantCommand.land())
                .telefoonnummer(createKlantCommand.telefoonnummer())
                .mobiel(createKlantCommand.mobiel())
                .email(createKlantCommand.email())
                .btwNummer(createKlantCommand.btwNummer())
                .opmerkingen(createKlantCommand.opmerkingen())
                .build();

        createKlantPorts.forEach(port -> port.createKlant(klant));

        return klant.getKlantId();
    }
}
