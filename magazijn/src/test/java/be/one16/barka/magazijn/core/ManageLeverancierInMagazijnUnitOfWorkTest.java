package be.one16.barka.magazijn.core;

import be.one16.barka.magazijn.common.StatusLeverancier;
import be.one16.barka.magazijn.common.TestDataBuilder;
import be.one16.barka.magazijn.domain.Artikel;
import be.one16.barka.magazijn.domain.Leverancier;
import be.one16.barka.magazijn.ports.in.CreateArtikelLeverancierCommand;
import be.one16.barka.magazijn.ports.in.DeleteArtikelLeverancierCommand;
import be.one16.barka.magazijn.ports.in.UpdateArtikelLeverancierCommand;
import be.one16.barka.magazijn.ports.out.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ManageLeverancierInMagazijnUnitOfWorkTest{

    @Captor
    ArgumentCaptor<Leverancier> leverancierCaptor;

    @Captor
    ArgumentCaptor<UUID> leverancierUUIDCaptor;


    UUID uuid = UUID.randomUUID();

    @Test
    void createArtikelLeverancierInMagazijn() {
        ArtikelLeverancierCreatePort artikelLeverancierCreatePort= Mockito.mock(ArtikelLeverancierCreatePort.class);

        ManageLeverancierInMagazijnUnitOfWork manageLeverancierInMagazijnUnitOfWork = new ManageLeverancierInMagazijnUnitOfWork(List.of(artikelLeverancierCreatePort), null, null,null, null);
        manageLeverancierInMagazijnUnitOfWork.createArtikelLeverancierInMagazijn(new CreateArtikelLeverancierCommand(uuid,"Sinalco"));
        Mockito.verify(artikelLeverancierCreatePort).createArtikelLeverancier(leverancierCaptor.capture());
        Leverancier leverancier = leverancierCaptor.getValue();
        assertEquals(leverancier.getLeverancierId(),uuid);
        assertEquals("Sinalco",leverancier.getNaam());
        assertEquals(leverancier.getStatus(), StatusLeverancier.ACTIEF);

    }

    @Test
    void updateArtikelLeverancierInMagazijnWhenLeverancierIsNonExisting() {

        ArtikelLeverancierCreatePort artikelLeverancierCreatePort= Mockito.mock(ArtikelLeverancierCreatePort.class);
        LoadArtikelLeveranciersPort loadArtikelLeveranciersPort = Mockito.mock(LoadArtikelLeveranciersPort.class);
        ManageLeverancierInMagazijnUnitOfWork manageLeverancierInMagazijnUnitOfWork = new ManageLeverancierInMagazijnUnitOfWork(List.of(artikelLeverancierCreatePort),null,null,loadArtikelLeveranciersPort, null);
        Mockito.when(loadArtikelLeveranciersPort.retrieveArtikelLeverancierById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> manageLeverancierInMagazijnUnitOfWork.updateArtikelLeverancierInMagazijn(new UpdateArtikelLeverancierCommand(UUID.randomUUID(),"GVA",StatusLeverancier.ACTIEF)));

    }

    @Test
    void updateArtikelLeverancierInMagazijnWhenLeverancierIsExisting() {

        //Arrange
        ArtikelLeverancierUpdatePort artikelLeverancierUpdatePort= Mockito.mock(ArtikelLeverancierUpdatePort.class);
        LoadArtikelLeveranciersPort loadArtikelLeveranciersPort = Mockito.mock(LoadArtikelLeveranciersPort.class);
        Leverancier leverancierToUpdate = Leverancier.builder().leverancierId(UUID.randomUUID()).naam("Sinalco").build();
        ManageLeverancierInMagazijnUnitOfWork manageLeverancierInMagazijnUnitOfWork = new ManageLeverancierInMagazijnUnitOfWork(null,List.of(artikelLeverancierUpdatePort),null,loadArtikelLeveranciersPort, null);
        Mockito.when(loadArtikelLeveranciersPort.retrieveArtikelLeverancierById(any(UUID.class))).thenReturn(Optional.of(leverancierToUpdate));

        //Act
        manageLeverancierInMagazijnUnitOfWork.updateArtikelLeverancierInMagazijn(new UpdateArtikelLeverancierCommand(leverancierToUpdate.getLeverancierId(),"Sinalco updated",StatusLeverancier.ACTIEF));

        //Assert
        Mockito.verify(artikelLeverancierUpdatePort).updateArtikelLeverancier(leverancierCaptor.capture());
        Leverancier leverancier = leverancierCaptor.getValue();
        assertEquals("Sinalco updated",leverancier.getNaam());

    }

    @Test
    void deleteArtikelLeverancierInMagazijnWhenLeverancierIsNonExisting() {

        //Arrange
        ArtikelLeverancierDeletePort artikelLeverancierDeletePort = Mockito.mock(ArtikelLeverancierDeletePort.class);
        LoadArtikelLeveranciersPort loadArtikelLeveranciersPort = Mockito.mock(LoadArtikelLeveranciersPort.class);
        LoadArtikelsPort loadArtikelsPort = Mockito.mock(LoadArtikelsPort.class);
        ManageLeverancierInMagazijnUnitOfWork manageLeverancierInMagazijnUnitOfWork = new ManageLeverancierInMagazijnUnitOfWork(null,null,List.of(artikelLeverancierDeletePort),loadArtikelLeveranciersPort, loadArtikelsPort);
        Mockito.when(loadArtikelLeveranciersPort.retrieveArtikelLeverancierById(any(UUID.class))).thenReturn(Optional.empty());
        DeleteArtikelLeverancierCommand deleteArtikelLeverancierCommand = new DeleteArtikelLeverancierCommand(UUID.randomUUID(),"Test");
        //Act and assert
        assertThrows(IllegalArgumentException.class, () -> manageLeverancierInMagazijnUnitOfWork.deleteArtikelLeverancierInMagazijn(deleteArtikelLeverancierCommand));

    }

    @Test
    void deleteArtikelLeverancierInMagazijnWhenLeverancierIsExisting() {

        //Arrange
        ArtikelLeverancierDeletePort artikelLeverancierDeletePort = Mockito.mock(ArtikelLeverancierDeletePort.class);
        ArtikelLeverancierUpdatePort artikelLeverancierUpdatePort = Mockito.mock(ArtikelLeverancierUpdatePort.class);
        LoadArtikelLeveranciersPort loadArtikelLeveranciersPort = Mockito.mock(LoadArtikelLeveranciersPort.class);
        LoadArtikelsPort loadArtikelsPort = Mockito.mock(LoadArtikelsPort.class);
        Leverancier leverancier = Leverancier.builder().leverancierId(UUID.randomUUID()).naam("Sinalco").build();
        ManageLeverancierInMagazijnUnitOfWork manageLeverancierInMagazijnUnitOfWork = new ManageLeverancierInMagazijnUnitOfWork(null,List.of(artikelLeverancierUpdatePort),List.of(artikelLeverancierDeletePort),loadArtikelLeveranciersPort, loadArtikelsPort);
        Mockito.when(loadArtikelLeveranciersPort.retrieveArtikelLeverancierById(any(UUID.class))).thenReturn(Optional.of(leverancier));
        Mockito.when(loadArtikelsPort.leverancierHasArticles(leverancier.getLeverancierId())).thenReturn(false);
        DeleteArtikelLeverancierCommand deleteArtikelLeverancierCommand = new DeleteArtikelLeverancierCommand(leverancier.getLeverancierId(), leverancier.getNaam());
        //Act
        manageLeverancierInMagazijnUnitOfWork.deleteArtikelLeverancierInMagazijn(deleteArtikelLeverancierCommand);

        //Assert
        Mockito.verify(artikelLeverancierDeletePort).deleteArtikelLeverancier(leverancierUUIDCaptor.capture());
        UUID leverancierUUID = leverancierUUIDCaptor.getValue();
        assertEquals(leverancier.getLeverancierId(),leverancierUUID);
    }

    @Test
    void softDeleteWhenThereAreArticlesLinkedToLeverancier() {

        //Arrange
        ArtikelLeverancierDeletePort artikelLeverancierDeletePort = Mockito.mock(ArtikelLeverancierDeletePort.class);
        ArtikelLeverancierUpdatePort artikelLeverancierUpdatePort = Mockito.mock(ArtikelLeverancierUpdatePort.class);
        LoadArtikelLeveranciersPort loadArtikelLeveranciersPort = Mockito.mock(LoadArtikelLeveranciersPort.class);
        LoadArtikelsPort loadArtikelsPort = Mockito.mock(LoadArtikelsPort.class);
        Leverancier leverancier = Leverancier.builder().leverancierId(UUID.randomUUID()).naam("Sinalco").build();
        ManageLeverancierInMagazijnUnitOfWork manageLeverancierInMagazijnUnitOfWork = new ManageLeverancierInMagazijnUnitOfWork(null,List.of(artikelLeverancierUpdatePort),List.of(artikelLeverancierDeletePort),loadArtikelLeveranciersPort, loadArtikelsPort);
        Mockito.when(loadArtikelLeveranciersPort.retrieveArtikelLeverancierById(any(UUID.class))).thenReturn(Optional.of(leverancier));
        Mockito.when(loadArtikelsPort.leverancierHasArticles(leverancier.getLeverancierId())).thenReturn(true);
        DeleteArtikelLeverancierCommand deleteArtikelLeverancierCommand = new DeleteArtikelLeverancierCommand(leverancier.getLeverancierId(), leverancier.getNaam());
        //Act
        manageLeverancierInMagazijnUnitOfWork.deleteArtikelLeverancierInMagazijn(deleteArtikelLeverancierCommand);

        //Assert
        Mockito.verify(artikelLeverancierUpdatePort).updateArtikelLeverancier(leverancierCaptor.capture());
        Leverancier leverancierCaptured = leverancierCaptor.getValue();
        assertEquals(leverancierCaptured.getStatus(),StatusLeverancier.INACTIEF);
    }


}