package be.one16.barka.klant.core.materiaal;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.klant.domain.Materiaal;
import be.one16.barka.klant.ports.in.materiaal.CreateMateriaalCommand;
import be.one16.barka.klant.ports.in.materiaal.CreateMateriaalUnitOfWork;
import be.one16.barka.klant.ports.out.materiaal.MateriaalCreatePort;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static be.one16.barka.klant.core.materiaal.MateriaalUtil.*;

@UnitOfWork
public class DefaultCreateMateriaalUnitOfWork implements CreateMateriaalUnitOfWork {

    private final List<MateriaalCreatePort> materiaalCreatePorts;

    public DefaultCreateMateriaalUnitOfWork(List<MateriaalCreatePort> materiaalCreatePorts) {

        this.materiaalCreatePorts = materiaalCreatePorts;
    }

    @Override
    public UUID createMateriaal(CreateMateriaalCommand createMateriaalCommand) {

        int aantalArtikels = createMateriaalCommand.aantalArtikels();
        BigDecimal verkoopPrijsArtikel = createMateriaalCommand.verkoopPrijsArtikel();
        int korting = createMateriaalCommand.korting();
        int btwPerc = createMateriaalCommand.btwPerc();
        BigDecimal totaalInclusBtw = calculateTotaalInclusBtwWithDiscount(aantalArtikels,verkoopPrijsArtikel,korting);
        BigDecimal totaalExclusBtw = calculateTotaalExclusBtw(totaalInclusBtw,btwPerc);
        BigDecimal btwBedrag = calculateBtwBedrag(totaalInclusBtw,totaalExclusBtw);

        Materiaal materiaal = Materiaal.builder()
                .materiaalId(UUID.randomUUID())
                .artikelId(createMateriaalCommand.artikelId())
                .artikelMerk(createMateriaalCommand.artikelMerk())
                .artikelCode(createMateriaalCommand.artikelCode())
                .artikelOmschrijving(createMateriaalCommand.artikelOmschrijving())
                .aantalArtikels(aantalArtikels)
                .verkoopPrijsArtikel(verkoopPrijsArtikel)
                .korting(korting)
                .btwPerc(btwPerc)
                .totaalExclusBtw(totaalExclusBtw)
                .totaalInclusBtw(totaalInclusBtw)
                .btwBedrag(btwBedrag)
                .orderId(createMateriaalCommand.orderId())
                .build();

        materiaalCreatePorts.forEach(port -> port.createMateriaal(materiaal));

        return materiaal.getMateriaalId();
    }

}
