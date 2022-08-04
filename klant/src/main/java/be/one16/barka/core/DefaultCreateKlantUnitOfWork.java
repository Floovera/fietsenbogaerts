package be.one16.barka.core;

import be.one16.barka.domain.Klant;
import be.one16.barka.port.in.CreateKlantCommand;
import be.one16.barka.port.in.CreateKlantUnitOfWork;
import be.one16.barka.port.out.CreateKlantPort;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static be.one16.barka.common.KlantType.ONBEKEND;

@Service
public class DefaultCreateKlantUnitOfWork implements CreateKlantUnitOfWork {

    @Autowired
    private List<CreateKlantPort> createKlantPorts;

    @Override
    public UUID createKlant(CreateKlantCommand createKlantCommand) {
        if (StringUtils.isEmpty(createKlantCommand.naam())) {
            throw new IllegalArgumentException("Value for 'naam' can not be null or empty");
        }

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
