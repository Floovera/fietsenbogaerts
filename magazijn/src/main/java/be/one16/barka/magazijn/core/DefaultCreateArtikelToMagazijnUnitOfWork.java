package be.one16.barka.magazijn.core;

import be.one16.barka.magazijn.domain.Artikel;
import be.one16.barka.magazijn.ports.in.CreateArtikelCommand;
import be.one16.barka.magazijn.ports.in.CreateArtikelToMagazijnUnitOfWork;
import be.one16.barka.magazijn.ports.out.ArtikelCreatePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultCreateArtikelToMagazijnUnitOfWork implements CreateArtikelToMagazijnUnitOfWork {

    private final List<ArtikelCreatePort> artikelCreatePorts;


    public DefaultCreateArtikelToMagazijnUnitOfWork(List<ArtikelCreatePort> artikelCreatePorts) {
        this.artikelCreatePorts = artikelCreatePorts;
    }

    @Override
    @Transactional
    public UUID createArtikelInMagazijn(CreateArtikelCommand createArtikelCommand) {
        Artikel artikel = Artikel.builder()
                .artikelId(UUID.randomUUID())
                .merk(createArtikelCommand.merk())
                .code(createArtikelCommand.code())
                .omschrijving(createArtikelCommand.omschrijving())
                .minimumInStock(createArtikelCommand.minimumInStock())
                .aantalInStock(createArtikelCommand.aantalInStock())
                .aankoopPrijs(createArtikelCommand.aankoopPrijs())
                .verkoopPrijs(createArtikelCommand.verkoopPrijs())
                .actuelePrijs(createArtikelCommand.actuelePrijs())
                .build();

        //code moet uniek zijn
        //prijzen verkoop en actuele prijs moet steeds meer zijn dan aankoopprijs

        artikelCreatePorts.forEach(port -> port.artikelCreated(artikel));

        return artikel.getArtikelId();
    }
}
