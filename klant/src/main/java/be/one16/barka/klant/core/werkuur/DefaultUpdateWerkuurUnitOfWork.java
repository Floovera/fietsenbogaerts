package be.one16.barka.klant.core.werkuur;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.klant.domain.Werkuur;
import be.one16.barka.klant.ports.in.werkuur.UpdateWerkuurCommand;
import be.one16.barka.klant.ports.in.werkuur.UpdateWerkuurUnitOfWork;
import be.one16.barka.klant.ports.out.werkuur.WerkuurUpdatePort;
import java.util.List;

@UnitOfWork
public class DefaultUpdateWerkuurUnitOfWork implements UpdateWerkuurUnitOfWork {

    private final List<WerkuurUpdatePort> werkuurUpdatePorts;

    public DefaultUpdateWerkuurUnitOfWork(List<WerkuurUpdatePort> werkuurUpdatePorts) {
        this.werkuurUpdatePorts = werkuurUpdatePorts;
    }

    @Override
    public void updateWerkuur(UpdateWerkuurCommand updateWerkuurCommand) {
        Werkuur werkuur = Werkuur.builder()
                .werkuurId(updateWerkuurCommand.werkuurId())
                .datum(updateWerkuurCommand.datum())
                .aantalUren(updateWerkuurCommand.aantalUren())
                .uurTarief(updateWerkuurCommand.uurTarief())
                .btwPerc(updateWerkuurCommand.btwPerc())
                .totaalExclusBtw(updateWerkuurCommand.totaalExclusBtw())
                .totaalInclusBtw(updateWerkuurCommand.totaalInclusBtw())
                .btwBedrag(updateWerkuurCommand.btwBedrag())
                .verkoopId(updateWerkuurCommand.verkoopId())
                .build();

        werkuurUpdatePorts.forEach(port -> port.updateWerkuur(werkuur));
    }

}
