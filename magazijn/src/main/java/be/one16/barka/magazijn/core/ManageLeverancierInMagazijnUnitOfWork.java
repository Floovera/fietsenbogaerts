package be.one16.barka.magazijn.core;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.magazijn.common.StatusLeverancier;
import be.one16.barka.magazijn.domain.Artikel;
import be.one16.barka.magazijn.domain.Leverancier;
import be.one16.barka.magazijn.ports.in.*;
import be.one16.barka.magazijn.ports.out.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@UnitOfWork
public class ManageLeverancierInMagazijnUnitOfWork implements CreateArtikelLeverancierInMagazijnUnitOfWork, UpdateArtikelLeverancierInMagazijnUnitOfWork, DeleteArtikelLeverancierInMagazijnUnitOfWork, ArtikelLeveranciersInMagazijnQuery {

    private final List<ArtikelLeverancierCreatePort> artikelLeverancierCreatePorts;
    private final List<ArtikelLeverancierUpdatePort> artikelLeverancierUpdatePorts;
    private final List<ArtikelLeverancierDeletePort> artikelLeverancierDeletePorts;
    private final LoadArtikelLeveranciersPort loadArtikelLeveranciersPort;
    private final LoadArtikelsPort loadArtikelsPort;

    public ManageLeverancierInMagazijnUnitOfWork(List<ArtikelLeverancierCreatePort> artikelLeverancierCreatePorts, List<ArtikelLeverancierUpdatePort> artikelLeverancierUpdatePorts, List<ArtikelLeverancierDeletePort> artikelLeverancierDeletePorts, LoadArtikelLeveranciersPort loadArtikelLeveranciersPort, LoadArtikelsPort loadArtikelsPort) {
        this.artikelLeverancierCreatePorts = artikelLeverancierCreatePorts;
        this.artikelLeverancierUpdatePorts = artikelLeverancierUpdatePorts;
        this.artikelLeverancierDeletePorts = artikelLeverancierDeletePorts;
        this.loadArtikelLeveranciersPort = loadArtikelLeveranciersPort;
        this.loadArtikelsPort = loadArtikelsPort;
    }

    @Override
    public UUID createArtikelLeverancierInMagazijn(CreateArtikelLeverancierCommand createArtikelLeverancierCommand) {
        Leverancier leverancier = Leverancier.builder()
                .leverancierId(createArtikelLeverancierCommand.uuid())
                .naam(createArtikelLeverancierCommand.naam())
                .status(StatusLeverancier.ACTIEF)
                .build();

        artikelLeverancierCreatePorts.forEach(port -> port.createArtikelLeverancier(leverancier));

        return leverancier.getLeverancierId();
    }

    @Override
    public void updateArtikelLeverancierInMagazijn(UpdateArtikelLeverancierCommand updateArtikelLeverancierCommand) {
        Leverancier leverancier = Leverancier.builder()
                .leverancierId(updateArtikelLeverancierCommand.uuid())
                .naam(updateArtikelLeverancierCommand.naam())
                .status(updateArtikelLeverancierCommand.status())
                .build();

        Optional<Leverancier> leverancierRetrieved =  retrieveArtikelLeverancierFromMagazijnById(leverancier.getLeverancierId());
        if(leverancierRetrieved.isEmpty()){
            throw new IllegalArgumentException("Dit leverancierId werd niet terug gevonden.");
        }

        artikelLeverancierUpdatePorts.forEach(port -> port.updateArtikelLeverancier(leverancier));
    }

    @Override
    public void deleteArtikelLeverancierInMagazijn(DeleteArtikelLeverancierCommand deleteArtikelLeverancierCommand) {
        Leverancier leverancier = Leverancier.builder()
                .leverancierId(deleteArtikelLeverancierCommand.uuid())
                .naam(deleteArtikelLeverancierCommand.naam())
                .status(StatusLeverancier.INACTIEF)
                .build();

        Optional<Leverancier> leverancierRetrieved =  retrieveArtikelLeverancierFromMagazijnById(leverancier.getLeverancierId());
        if(leverancierRetrieved.isEmpty()){
            throw new IllegalArgumentException("Dit leverancierId werd niet terug gevonden.");
        }

        boolean artikelsFound = loadArtikelsPort.leverancierHasArticles(leverancier.getLeverancierId());
        if (artikelsFound) {
            artikelLeverancierUpdatePorts.forEach(port -> port.updateArtikelLeverancier(leverancier));
        } else {
            artikelLeverancierDeletePorts.forEach(port -> port.deleteArtikelLeverancier(leverancier.getLeverancierId()));
        }
        }


    @Override
    public Optional<Leverancier> retrieveArtikelLeverancierFromMagazijnById(UUID id) {
        return loadArtikelLeveranciersPort.retrieveArtikelLeverancierById(id);
    };

}
