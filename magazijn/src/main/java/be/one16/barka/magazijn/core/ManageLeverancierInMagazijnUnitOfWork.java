package be.one16.barka.magazijn.core;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.magazijn.domain.Artikel;
import be.one16.barka.magazijn.domain.Leverancier;
import be.one16.barka.magazijn.ports.in.*;
import be.one16.barka.magazijn.ports.out.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@UnitOfWork
public class ManageLeverancierInMagazijnUnitOfWork implements CreateArtikelLeverancierInMagazijnUnitOfWork, UpdateArtikelLeverancierInMagazijnUnitOfWork, DeleteArtikelLeverancierInMagazijnUnitOfWork, ArtikelLeveranciersInMagazijnQuery {

    private final List<ArtikelLeverancierCreatePort> artikelLeverancierCreatePorts;
    private final List<ArtikelLeverancierUpdatePort> artikelLeverancierUpdatePorts;
    private final List<ArtikelLeverancierDeletePort> artikelLeverancierDeletePorts;
    private final LoadArtikelLeveranciersPort loadArtikelLeveranciersPort;


    public ManageLeverancierInMagazijnUnitOfWork(List<ArtikelLeverancierCreatePort> artikelLeverancierCreatePorts, List<ArtikelLeverancierUpdatePort> artikelLeverancierUpdatePorts, List<ArtikelLeverancierDeletePort> artikelLeverancierDeletePorts, LoadArtikelLeveranciersPort loadArtikelLeveranciersPort) {
        this.artikelLeverancierCreatePorts = artikelLeverancierCreatePorts;
        this.artikelLeverancierUpdatePorts = artikelLeverancierUpdatePorts;
        this.artikelLeverancierDeletePorts = artikelLeverancierDeletePorts;
        this.loadArtikelLeveranciersPort = loadArtikelLeveranciersPort;
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

        Optional<Leverancier> leverancierRetrieved =  retrieveArtikelLeverancierFromMagazijnById(leverancier.getLeverancierId());
        if(leverancierRetrieved.isEmpty()){
            throw new RuntimeException("Bestaat niet");
        }else{
            artikelLeverancierUpdatePorts.forEach(port -> port.updateArtikelLeverancier(leverancier));
        }
    }

    @Override
    public void deleteArtikelLeverancierInMagazijn(UUID LeverancierId) {
        artikelLeverancierDeletePorts.forEach(port -> port.deleteArtikelLeverancier(LeverancierId));
    }

    @Override
    public Optional<Leverancier> retrieveArtikelLeverancierFromMagazijnById(UUID id) {
        return loadArtikelLeveranciersPort.retrieveArtikelLeverancierById(id);
    }};
