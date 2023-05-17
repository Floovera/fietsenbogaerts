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

        double aantalUren = createWerkuurCommand.aantalUren();
        double uurTarief = createWerkuurCommand.uurTarief();
        int btwPerc = createWerkuurCommand.btwPerc();
        double totaalInclusBtw = calculateTotaalInclusBtw(aantalUren,uurTarief);
        double totaalExclusBtw = calculateTotaalExclusBtw(totaalInclusBtw,btwPerc);
        double btwBedrag = calculateBtwBedrag(totaalInclusBtw,totaalExclusBtw);



        Werkuur werkuur = Werkuur.builder()
                .werkuurId(UUID.randomUUID())
                .datum(createWerkuurCommand.datum())
                .aantalUren(createWerkuurCommand.aantalUren())
                .uurTarief(createWerkuurCommand.uurTarief())
                .btwPerc(createWerkuurCommand.btwPerc())
                .totaalExclusBtw(totaalExclusBtw)
                .totaalInclusBtw(totaalInclusBtw)
                .btwBedrag(btwBedrag)
                .verkoopId(createWerkuurCommand.verkoopId())
                .build();

        werkuurCreatePorts.forEach(port -> port.createWerkuur(werkuur));

        return werkuur.getWerkuurId();
    }

    private double calculateTotaalInclusBtw(double aantalUren, double uurTarief){

        double totaalInclus =  aantalUren * uurTarief;
        return totaalInclus;
    }

    private double calculateTotaalExclusBtw(double totaalInclusBtw, int btwPerc){
        double totaalExclus =  totaalInclusBtw / (1+(btwPerc/100.0));
        return totaalExclus;
    }

    private double calculateBtwBedrag(double totaalInclusBtw, double totaalExlcusBtw){

        double btwBedrag =  totaalInclusBtw - totaalExlcusBtw;
        return btwBedrag;
    }

}
