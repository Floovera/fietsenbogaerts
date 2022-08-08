package be.one16.barka.leverancier.core;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.leverancier.domain.Leverancier;
import be.one16.barka.leverancier.ports.in.UpdateLeverancierCommand;
import be.one16.barka.leverancier.ports.in.UpdateLeverancierUnitOfWork;
import be.one16.barka.leverancier.ports.out.LeverancierUpdatePort;

import java.util.List;

@UnitOfWork
public class DefaultUpdateLeverancierUnitOfWork implements UpdateLeverancierUnitOfWork {

    private final List<LeverancierUpdatePort> leverancierUpdatePorts;

    public DefaultUpdateLeverancierUnitOfWork(List<LeverancierUpdatePort> leverancierUpdatePorts) {
        this.leverancierUpdatePorts = leverancierUpdatePorts;
    }

    @Override
    public void updateLeverancier(UpdateLeverancierCommand updateLeverancierCommand) {
        Leverancier leverancier = Leverancier.builder()
                .leverancierId(updateLeverancierCommand.leverancierId())
                .naam(updateLeverancierCommand.naam())
                .straat(updateLeverancierCommand.straat())
                .huisnummer(updateLeverancierCommand.huisnummer())
                .bus(updateLeverancierCommand.bus())
                .postcode(updateLeverancierCommand.postcode())
                .gemeente(updateLeverancierCommand.gemeente())
                .land(updateLeverancierCommand.land())
                .telefoonnummer(updateLeverancierCommand.telefoonnummer())
                .mobiel(updateLeverancierCommand.mobiel())
                .fax(updateLeverancierCommand.fax())
                .email(updateLeverancierCommand.email())
                .btwNummer(updateLeverancierCommand.btwNummer())
                .opmerkingen(updateLeverancierCommand.opmerkingen())
                .build();

        leverancierUpdatePorts.forEach(port -> port.updateLeverancier(leverancier));
    }
}
