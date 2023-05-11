package be.one16.barka.magazijn.core;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.magazijn.domain.Leverancier;
import be.one16.barka.magazijn.ports.in.CreateArtikelLeverancierCommand;
import be.one16.barka.magazijn.ports.in.CreateArtikelLeverancierInMagazijnUnitOfWork;
import be.one16.barka.magazijn.ports.out.ArtikelCreatePort;
import be.one16.barka.magazijn.ports.out.ArtikelLeverancierCreatePort;
import be.one16.barka.magazijn.ports.out.ArtikelUniqueCodePort;

import java.util.List;
import java.util.UUID;

@UnitOfWork
public class ManageLeverancierInMagazijnUnitOfWork implements CreateArtikelLeverancierInMagazijnUnitOfWork {

    private final List<ArtikelLeverancierCreatePort> artikelLeverancierCreatePorts;

    public ManageLeverancierInMagazijnUnitOfWork(List<ArtikelLeverancierCreatePort> artikelLeverancierCreatePorts) {
        this.artikelLeverancierCreatePorts = artikelLeverancierCreatePorts;
    }

    @Override
    public UUID createArtikelLeverancierInMagazijn(CreateArtikelLeverancierCommand createArtikelLeverancierCommand) {
        Leverancier leverancier = Leverancier.builder()
                .leverancierId(UUID.randomUUID())
                .naam(createArtikelLeverancierCommand.naam())
                .build();

        artikelLeverancierCreatePorts.forEach(port -> port.createArtikelLeverancier(leverancier));

        return leverancier.getLeverancierId();
    }
}
