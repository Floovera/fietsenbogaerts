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
        if (StringUtils.isEmpty(updateKlantCommand.getNaam())) {
            throw new IllegalArgumentException("Value for 'naam' can not be null or empty");
        }

        Klant klant = Klant.builder()
                .klantId(updateKlantCommand.getKlantId())
                .naam(updateKlantCommand.getNaam())
                .klantType(updateKlantCommand.getKlantType() == null ? ONBEKEND : updateKlantCommand.getKlantType())
                .straat(updateKlantCommand.getStraat())
                .huisnummer(updateKlantCommand.getHuisnummer())
                .bus(updateKlantCommand.getBus())
                .postcode(updateKlantCommand.getPostcode())
                .gemeente(updateKlantCommand.getGemeente())
                .land(updateKlantCommand.getLand())
                .telefoonnummer(updateKlantCommand.getTelefoonnummer())
                .mobiel(updateKlantCommand.getMobiel())
                .email(updateKlantCommand.getEmail())
                .btwNummer(updateKlantCommand.getBtwNummer())
                .opmerkingen(updateKlantCommand.getOpmerkingen())
                .build();

        updateKlantPorts.forEach(port -> port.updateKlant(klant));
    }
}
