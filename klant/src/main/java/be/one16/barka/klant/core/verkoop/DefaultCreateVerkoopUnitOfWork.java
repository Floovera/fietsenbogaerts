package be.one16.barka.klant.core.verkoop;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.klant.domain.Verkoop;
import be.one16.barka.klant.port.in.verkoop.CreateVerkoopCommand;
import be.one16.barka.klant.port.in.verkoop.CreateVerkoopUnitOfWork;
import be.one16.barka.klant.port.out.verkoop.CreateVerkoopPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static be.one16.barka.klant.common.KlantType.ONBEKEND;

@UnitOfWork
public class DefaultCreateVerkoopUnitOfWork implements CreateVerkoopUnitOfWork {

    private final List<CreateVerkoopPort> createVerkoopPorts;

    public DefaultCreateVerkoopUnitOfWork(List<CreateVerkoopPort> createVerkoopPorts) {
        this.createVerkoopPorts = createVerkoopPorts;
    }

    @Override
    @Transactional
    public UUID createVerkoop(CreateVerkoopCommand createVerkoopCommand) {
        Verkoop verkoop = Verkoop.builder()
                .verkoopId(UUID.randomUUID())
                .naam(createVerkoopCommand.naam())
                .opmerkingen(createVerkoopCommand.opmerkingen())
                .klantId(createVerkoopCommand.klantId())
                .build();

        createVerkoopPorts.forEach(port -> port.createVerkoop(verkoop));

        return verkoop.getVerkoopId();
    }
}
