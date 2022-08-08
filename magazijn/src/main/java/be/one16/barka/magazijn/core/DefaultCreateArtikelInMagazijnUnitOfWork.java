package be.one16.barka.magazijn.core;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.magazijn.domain.Artikel;
import be.one16.barka.magazijn.domain.Leverancier;
import be.one16.barka.magazijn.ports.in.CreateArtikelCommand;
import be.one16.barka.magazijn.ports.in.CreateArtikelInMagazijnUnitOfWork;
import be.one16.barka.magazijn.ports.out.ArtikelCreatePort;
import be.one16.barka.magazijn.ports.out.ArtikelUniqueCodePort;

import java.util.List;
import java.util.UUID;

@UnitOfWork
public class DefaultCreateArtikelInMagazijnUnitOfWork implements CreateArtikelInMagazijnUnitOfWork {

    private final List<ArtikelCreatePort> artikelCreatePorts;
    private final ArtikelUniqueCodePort artikelUniqueCodePort;

    public DefaultCreateArtikelInMagazijnUnitOfWork(List<ArtikelCreatePort> artikelCreatePorts, ArtikelUniqueCodePort artikelUniqueCodePort) {
        this.artikelCreatePorts = artikelCreatePorts;
        this.artikelUniqueCodePort = artikelUniqueCodePort;
    }

    @Override
    public UUID createArtikelInMagazijn(CreateArtikelCommand createArtikelCommand) {
        Artikel artikel = Artikel.builder()
                .artikelId(UUID.randomUUID())
                .merk(createArtikelCommand.merk())
                .code(createArtikelCommand.code())
                .omschrijving(createArtikelCommand.omschrijving())
                .leverancier(Leverancier.builder().leverancierId(createArtikelCommand.leverancierId()).build())
                .aantalInStock(createArtikelCommand.aantalInStock())
                .minimumInStock(createArtikelCommand.minimumInStock())
                .aankoopPrijs(createArtikelCommand.aankoopPrijs())
                .verkoopPrijs(createArtikelCommand.verkoopPrijs())
                .actuelePrijs(createArtikelCommand.actuelePrijs())
                .build();

        // Artikel code moet uniek zijn
        if (!artikelUniqueCodePort.checkUniqueArtikelCode(artikel.getCode())) {
            throw new IllegalStateException(String.format("Artikel code %s is al in gebruik", artikel.getCode()));
        }

        // Verkoop prijs en actuele prijs moeten steeds meer zijn dan aankoop prijs
        if (artikel.getVerkoopPrijs().doubleValue() <= artikel.getAankoopPrijs().doubleValue() || artikel.getActuelePrijs().doubleValue() <= artikel.getAankoopPrijs().doubleValue()) {
            throw new IllegalStateException("Value for 'verkoopPrijs' and 'actuelePrijs' must be higher than 'aankoopPrijs'");
        }

        artikelCreatePorts.forEach(port -> port.createArtikel(artikel));

        return artikel.getArtikelId();
    }
}
