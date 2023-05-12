package be.one16.barka.magazijn.core;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.magazijn.domain.Leverancier;
import be.one16.barka.magazijn.ports.in.CreateArtikelLeverancierCommand;
import be.one16.barka.magazijn.ports.in.CreateArtikelLeverancierInMagazijnUnitOfWork;
import be.one16.barka.magazijn.ports.in.UpdateArtikelLeverancierCommand;
import be.one16.barka.magazijn.ports.in.UpdateArtikelLeverancierInMagazijnUnitOfWork;
import be.one16.barka.magazijn.ports.out.ArtikelLeverancierCreatePort;
import be.one16.barka.magazijn.ports.out.ArtikelLeverancierUpdatePort;

import java.util.List;
import java.util.UUID;

@UnitOfWork
public class ManageLeverancierInMagazijnUnitOfWork implements CreateArtikelLeverancierInMagazijnUnitOfWork, UpdateArtikelLeverancierInMagazijnUnitOfWork {

    private final List<ArtikelLeverancierCreatePort> artikelLeverancierCreatePorts;
    private final List<ArtikelLeverancierUpdatePort> artikelLeverancierUpdatePorts;
    public ManageLeverancierInMagazijnUnitOfWork(List<ArtikelLeverancierCreatePort> artikelLeverancierCreatePorts, List<ArtikelLeverancierUpdatePort> artikelLeverancierUpdatePorts) {
        this.artikelLeverancierCreatePorts = artikelLeverancierCreatePorts;
        this.artikelLeverancierUpdatePorts = artikelLeverancierUpdatePorts;
    }

    @Override
    public UUID createArtikelLeverancierInMagazijn(CreateArtikelLeverancierCommand createArtikelLeverancierCommand) {
        Leverancier leverancier = Leverancier.builder()
                .leverancierId(createArtikelLeverancierCommand.uuid())
                .naam(createArtikelLeverancierCommand.naam())
                .build();

        artikelLeverancierCreatePorts.forEach(port -> port.createArtikelLeverancier(leverancier));

        return leverancier.getLeverancierId();
    }

    @Override
    public void updateArtikelLeverancierInMagazijn(UpdateArtikelLeverancierCommand updateArtikelLeverancierCommand) {
        Leverancier leverancier = Leverancier.builder()
                .leverancierId(updateArtikelLeverancierCommand.uuid())
                .naam(updateArtikelLeverancierCommand.naam())
                .build();


        artikelLeverancierUpdatePorts.forEach(port -> port.updateArtikelLeverancier(leverancier));
    }


}
