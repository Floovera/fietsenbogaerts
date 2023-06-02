package be.one16.barka.klant.core.materiaal;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.klant.domain.Materiaal;
import be.one16.barka.klant.ports.in.materiaal.UpdateMateriaalCommand;
import be.one16.barka.klant.ports.in.materiaal.UpdateMateriaalUnitOfWork;
import be.one16.barka.klant.ports.out.materiaal.MateriaalUpdatePort;

import java.math.BigDecimal;
import java.util.List;

import static be.one16.barka.klant.core.materiaal.MateriaalUtil.*;

@UnitOfWork
public class DefaultUpdateMateriaalUnitOfWork implements UpdateMateriaalUnitOfWork {

    private final List<MateriaalUpdatePort> materiaalUpdatePorts;

    public DefaultUpdateMateriaalUnitOfWork(List<MateriaalUpdatePort> materiaalUpdatePorts) {

        this.materiaalUpdatePorts = materiaalUpdatePorts;
    }

    @Override
    public void updateMateriaal(UpdateMateriaalCommand updateMateriaalCommand) {

        int aantalArtikels = updateMateriaalCommand.aantalArtikels();
        BigDecimal verkoopPrijsArtikel = updateMateriaalCommand.verkoopPrijsArtikel();
        int korting = updateMateriaalCommand.korting();
        int btwPerc = updateMateriaalCommand.btwPerc();
        BigDecimal totaalInclusBtw = calculateTotaalInclusBtwWithDiscount(aantalArtikels,verkoopPrijsArtikel,korting);
        BigDecimal totaalExclusBtw = calculateTotaalExclusBtw(totaalInclusBtw,btwPerc);
        BigDecimal btwBedrag = calculateBtwBedrag(totaalInclusBtw,totaalExclusBtw);

        Materiaal materiaal = Materiaal.builder()
                .materiaalId(updateMateriaalCommand.materiaalId())
                .artikelId(updateMateriaalCommand.artikelId())
                .artikelMerk(updateMateriaalCommand.artikelMerk())
                .artikelCode(updateMateriaalCommand.artikelCode())
                .artikelOmschrijving(updateMateriaalCommand.artikelOmschrijving())
                .aantalArtikels(aantalArtikels)
                .verkoopPrijsArtikel(verkoopPrijsArtikel)
                .korting(korting)
                .btwPerc(btwPerc)
                .totaalExclusBtw(totaalExclusBtw)
                .totaalInclusBtw(totaalInclusBtw)
                .btwBedrag(btwBedrag)
                .orderId(updateMateriaalCommand.orderId())
                .build();

        materiaalUpdatePorts.forEach(port -> port.updateMateriaal(materiaal));
    }

}
