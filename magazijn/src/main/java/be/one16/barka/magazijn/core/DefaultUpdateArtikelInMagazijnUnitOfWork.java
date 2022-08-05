package be.one16.barka.magazijn.core;

import be.one16.barka.magazijn.domain.Artikel;
import be.one16.barka.magazijn.domain.Leverancier;
import be.one16.barka.magazijn.ports.in.UpdateArtikelCommand;
import be.one16.barka.magazijn.ports.in.UpdateArtikelInMagazijnUnitOfWork;
import be.one16.barka.magazijn.ports.out.ArtikelUniqueCodePort;
import be.one16.barka.magazijn.ports.out.ArtikelUpdatePort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultUpdateArtikelInMagazijnUnitOfWork implements UpdateArtikelInMagazijnUnitOfWork {

    private final List<ArtikelUpdatePort> artikelUpdatePortList;
    private final ArtikelUniqueCodePort artikelUniqueCodePort;

    public DefaultUpdateArtikelInMagazijnUnitOfWork(List<ArtikelUpdatePort> artikelUpdatePortList, ArtikelUniqueCodePort artikelUniqueCodePort) {
        this.artikelUpdatePortList = artikelUpdatePortList;
        this.artikelUniqueCodePort = artikelUniqueCodePort;
    }

    @Override
    public void updateArtikelInMagazijn(UpdateArtikelCommand updateArtikelCommand) {
        Artikel artikel = Artikel.builder()
                .artikelId(updateArtikelCommand.artikelId())
                .merk(updateArtikelCommand.merk())
                .code(updateArtikelCommand.code())
                .omschrijving(updateArtikelCommand.omschrijving())
                .leverancier(Leverancier.builder().leverancierId(updateArtikelCommand.leverancierId()).build())
                .aantalInStock(updateArtikelCommand.aantalInStock())
                .minimumInStock(updateArtikelCommand.minimumInStock())
                .aankoopPrijs(updateArtikelCommand.aankoopPrijs())
                .verkoopPrijs(updateArtikelCommand.verkoopPrijs())
                .actuelePrijs(updateArtikelCommand.actuelePrijs())
                .build();


        // Artikel code moet uniek zijn
        if (!artikelUniqueCodePort.checkUniqueArtikelCode(artikel.getCode())) {
            throw new IllegalStateException(String.format("Artikel code %s is al in gebruik", artikel.getCode()));
        }

        // Verkoop prijs en actuele prijs moeten steeds meer zijn dan aankoop prijs
        if (artikel.getVerkoopPrijs().doubleValue() <= artikel.getAankoopPrijs().doubleValue() || artikel.getActuelePrijs().doubleValue() <= artikel.getAankoopPrijs().doubleValue()) {
            throw new IllegalStateException("Value for 'verkoopPrijs' and 'actuelePrijs' must be higher than 'aankoopPrijs'");
        }

        artikelUpdatePortList.forEach(port -> port.updateArtikel(artikel));
    }
}
