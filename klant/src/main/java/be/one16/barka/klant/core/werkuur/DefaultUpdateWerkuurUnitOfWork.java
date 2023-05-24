package be.one16.barka.klant.core.werkuur;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.klant.domain.Werkuur;
import be.one16.barka.klant.ports.in.werkuur.UpdateWerkuurCommand;
import be.one16.barka.klant.ports.in.werkuur.UpdateWerkuurUnitOfWork;
import be.one16.barka.klant.ports.out.werkuur.WerkuurUpdatePort;
import java.util.List;

import static be.one16.barka.klant.core.werkuur.WerkuurUtil.*;

@UnitOfWork
public class DefaultUpdateWerkuurUnitOfWork implements UpdateWerkuurUnitOfWork {

    private final List<WerkuurUpdatePort> werkuurUpdatePorts;

    public DefaultUpdateWerkuurUnitOfWork(List<WerkuurUpdatePort> werkuurUpdatePorts) {
        this.werkuurUpdatePorts = werkuurUpdatePorts;
    }

    @Override
    public void updateWerkuur(UpdateWerkuurCommand updateWerkuurCommand) {

        double aantalUren = updateWerkuurCommand.aantalUren();
        double uurTarief = updateWerkuurCommand.uurTarief();
        int btwPerc = updateWerkuurCommand.btwPerc();
        double totaalInclusBtw = calculateTotaalInclusBtw(aantalUren,uurTarief);
        double totaalExclusBtw = calculateTotaalExclusBtw(totaalInclusBtw,btwPerc);
        double btwBedrag = calculateBtwBedrag(totaalInclusBtw,totaalExclusBtw);

        Werkuur werkuur = Werkuur.builder()
                .werkuurId(updateWerkuurCommand.werkuurId())
                .datum(updateWerkuurCommand.datum())
                .aantalUren(aantalUren)
                .uurTarief(uurTarief)
                .btwPerc(btwPerc)
                .totaalExclusBtw(totaalExclusBtw)
                .totaalInclusBtw(totaalInclusBtw)
                .btwBedrag(btwBedrag)
                .verkoopId(updateWerkuurCommand.verkoopId())
                .build();

        werkuurUpdatePorts.forEach(port -> port.updateWerkuur(werkuur));
    }

}
