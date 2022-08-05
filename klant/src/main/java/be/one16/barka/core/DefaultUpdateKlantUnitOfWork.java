package be.one16.barka.core;

import be.one16.barka.domain.Klant;
import be.one16.barka.port.in.UpdateKlantCommand;
import be.one16.barka.port.in.UpdateKlantUnitOfWork;
import be.one16.barka.port.out.UpdateKlantPort;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static be.one16.barka.common.KlantType.ONBEKEND;

@Service
public class DefaultUpdateKlantUnitOfWork implements UpdateKlantUnitOfWork {

    private final List<UpdateKlantPort> updateKlantPorts;

    public DefaultUpdateKlantUnitOfWork(List<UpdateKlantPort> updateKlantPorts) {
        this.updateKlantPorts = updateKlantPorts;
    }

    @Override
    public void updateKlant(UpdateKlantCommand updateKlantCommand) {
        if (StringUtils.isEmpty(updateKlantCommand.naam())) {
            throw new IllegalArgumentException("Value for 'naam' can not be null or empty");
        }

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
