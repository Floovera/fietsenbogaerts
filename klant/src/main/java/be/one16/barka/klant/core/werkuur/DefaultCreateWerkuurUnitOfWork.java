package be.one16.barka.klant.core.werkuur;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.klant.domain.Werkuur;
import be.one16.barka.klant.ports.in.werkuur.CreateWerkuurCommand;
import be.one16.barka.klant.ports.in.werkuur.CreateWerkuurUnitOfWork;
import be.one16.barka.klant.ports.out.werkuur.WerkuurCreatePort;

import java.util.List;
import java.util.UUID;

@UnitOfWork
public class DefaultCreateWerkuurUnitOfWork implements CreateWerkuurUnitOfWork {

    private final List<WerkuurCreatePort> werkuurCreatePorts;

    public DefaultCreateWerkuurUnitOfWork(List<WerkuurCreatePort> werkuurCreatePorts) {
        this.werkuurCreatePorts = werkuurCreatePorts;
    }

    @Override
    public UUID createWerkuur(CreateWerkuurCommand createWerkuurCommand) {
        Werkuur werkuur = Werkuur.builder()
                .werkuurId(UUID.randomUUID())
                .datum(createWerkuurCommand.datum())
                .aantalUren(createWerkuurCommand.aantalUren())
                .uurTarief(createWerkuurCommand.uurTarief())
                .btwPerc(createWerkuurCommand.btwPerc())
                .totaalExclusBtw(createWerkuurCommand.totaalExclusBtw())
                .totaalInclusBtw(createWerkuurCommand.totaalInclusBtw())
                .btwBedrag(createWerkuurCommand.btwBedrag())
                .verkoopId(createWerkuurCommand.verkoopId())
                .build();

        werkuurCreatePorts.forEach(port -> port.createWerkuur(werkuur));

        return werkuur.getWerkuurId();
    }

}
