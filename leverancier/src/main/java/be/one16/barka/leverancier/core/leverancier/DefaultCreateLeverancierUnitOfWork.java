package be.one16.barka.leverancier.core.leverancier;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.leverancier.domain.Leverancier;
import be.one16.barka.leverancier.ports.in.leverancier.CreateLeverancierCommand;
import be.one16.barka.leverancier.ports.in.leverancier.CreateLeverancierUnitOfWork;
import be.one16.barka.leverancier.ports.out.leverancier.LeverancierCreatePort;

import java.util.List;
import java.util.UUID;

@UnitOfWork
public class DefaultCreateLeverancierUnitOfWork implements CreateLeverancierUnitOfWork {

    private final List<LeverancierCreatePort> leverancierCreatePorts;

    public DefaultCreateLeverancierUnitOfWork(List<LeverancierCreatePort> leverancierCreatePorts) {
        this.leverancierCreatePorts = leverancierCreatePorts;
    }

    @Override
    public UUID createLeverancier(CreateLeverancierCommand createLeverancierCommand) {
        Leverancier leverancier = Leverancier.builder()
                .leverancierId(UUID.randomUUID())
                .naam(createLeverancierCommand.naam())
                .straat(createLeverancierCommand.straat())
                .huisnummer(createLeverancierCommand.huisnummer())
                .bus(createLeverancierCommand.bus())
                .postcode(createLeverancierCommand.postcode())
                .gemeente(createLeverancierCommand.gemeente())
                .land(createLeverancierCommand.land())
                .telefoonnummer(createLeverancierCommand.telefoonnummer())
                .mobiel(createLeverancierCommand.mobiel())
                .fax(createLeverancierCommand.fax())
                .email(createLeverancierCommand.email())
                .btwNummer(createLeverancierCommand.btwNummer())
                .opmerkingen(createLeverancierCommand.opmerkingen())
                .build();

        leverancierCreatePorts.forEach(port -> port.createLeverancier(leverancier));

        return leverancier.getLeverancierId();
    }
}
