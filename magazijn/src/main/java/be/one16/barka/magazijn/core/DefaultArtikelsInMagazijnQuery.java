package be.one16.barka.magazijn.core;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.magazijn.domain.Artikel;
import be.one16.barka.magazijn.ports.in.ArtikelsInMagazijnQuery;
import be.one16.barka.magazijn.ports.in.RetrieveArtikelFilterAndSortCommand;
import be.one16.barka.magazijn.ports.out.LoadArtikelsPort;
import org.springframework.data.domain.Page;

import java.util.UUID;

@UnitOfWork
public class DefaultArtikelsInMagazijnQuery implements ArtikelsInMagazijnQuery {

    private final LoadArtikelsPort loadArtikelsPort;

    public DefaultArtikelsInMagazijnQuery(LoadArtikelsPort loadArtikelsPort) {
        this.loadArtikelsPort = loadArtikelsPort;
    }

    @Override
    public Artikel retrieveArtikelFromMagazijnById(UUID id) {
        return loadArtikelsPort.retrieveArtikelById(id);
    }

    @Override
    public Page<Artikel> retrieveArtikelsByFilterAndSort(RetrieveArtikelFilterAndSortCommand retrieveArtikelFilterAndSortCommand) {
        return loadArtikelsPort.retrieveArtikelByFilterAndSort(retrieveArtikelFilterAndSortCommand.code(),
                retrieveArtikelFilterAndSortCommand.merk(),
                retrieveArtikelFilterAndSortCommand.omschrijving(),
                retrieveArtikelFilterAndSortCommand.leverancierId(),
                retrieveArtikelFilterAndSortCommand.pageable());
    }
}
