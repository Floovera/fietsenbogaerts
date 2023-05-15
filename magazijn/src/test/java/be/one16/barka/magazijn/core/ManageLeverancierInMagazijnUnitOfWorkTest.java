package be.one16.barka.magazijn.core;

import be.one16.barka.magazijn.domain.Leverancier;
import be.one16.barka.magazijn.ports.in.CreateArtikelLeverancierCommand;
import be.one16.barka.magazijn.ports.in.UpdateArtikelLeverancierCommand;
import be.one16.barka.magazijn.ports.out.ArtikelLeverancierCreatePort;
import be.one16.barka.magazijn.ports.out.ArtikelLeverancierDeletePort;
import be.one16.barka.magazijn.ports.out.ArtikelLeverancierUpdatePort;
import be.one16.barka.magazijn.ports.out.LoadArtikelLeveranciersPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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

        ManageLeverancierInMagazijnUnitOfWork manageLeverancierInMagazijnUnitOfWork = new ManageLeverancierInMagazijnUnitOfWork(List.of(artikelLeverancierCreatePort), null, null,null);
        manageLeverancierInMagazijnUnitOfWork.createArtikelLeverancierInMagazijn(new CreateArtikelLeverancierCommand(uuid,"Sinalco"));
        Mockito.verify(artikelLeverancierCreatePort).createArtikelLeverancier(leverancierCaptor.capture());
        Leverancier leverancier = leverancierCaptor.getValue();
        assertEquals(leverancier.getLeverancierId(),uuid);
        assertEquals("Sinalco",leverancier.getNaam());

    }

    @Test
    void updateArtikelLeverancierInMagazijnWhenLeverancierIsNonExisting() {

        ArtikelLeverancierCreatePort artikelLeverancierCreatePort= Mockito.mock(ArtikelLeverancierCreatePort.class);
        LoadArtikelLeveranciersPort loadArtikelLeveranciersPort = Mockito.mock(LoadArtikelLeveranciersPort.class);
        ManageLeverancierInMagazijnUnitOfWork manageLeverancierInMagazijnUnitOfWork = new ManageLeverancierInMagazijnUnitOfWork(List.of(artikelLeverancierCreatePort),null,null,loadArtikelLeveranciersPort);
        Mockito.when(loadArtikelLeveranciersPort.retrieveArtikelLeverancierById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> manageLeverancierInMagazijnUnitOfWork.updateArtikelLeverancierInMagazijn(new UpdateArtikelLeverancierCommand(UUID.randomUUID(),"GVA")));

    }

    @Test
    void updateArtikelLeverancierInMagazijnWhenLeverancierIsExisting() {

        //Arrange
        ArtikelLeverancierUpdatePort artikelLeverancierUpdatePort= Mockito.mock(ArtikelLeverancierUpdatePort.class);
        LoadArtikelLeveranciersPort loadArtikelLeveranciersPort = Mockito.mock(LoadArtikelLeveranciersPort.class);
        Leverancier leverancierToUpdate = Leverancier.builder().leverancierId(UUID.randomUUID()).naam("Sinalco").build();
        ManageLeverancierInMagazijnUnitOfWork manageLeverancierInMagazijnUnitOfWork = new ManageLeverancierInMagazijnUnitOfWork(null,List.of(artikelLeverancierUpdatePort),null,loadArtikelLeveranciersPort);
        Mockito.when(loadArtikelLeveranciersPort.retrieveArtikelLeverancierById(any(UUID.class))).thenReturn(Optional.of(leverancierToUpdate));

        //Act
        manageLeverancierInMagazijnUnitOfWork.updateArtikelLeverancierInMagazijn(new UpdateArtikelLeverancierCommand(leverancierToUpdate.getLeverancierId(),"Sinalco updated"));

        //Assert
        Mockito.verify(artikelLeverancierUpdatePort).updateArtikelLeverancier(leverancierCaptor.capture());
        Leverancier leverancier = leverancierCaptor.getValue();
        assertEquals("Sinalco updated",leverancier.getNaam());
    }

    @Test
    void deleteArtikelLeverancierInMagazijn() {

        //Arrange
        ArtikelLeverancierDeletePort artikelLeverancierDeletePort = Mockito.mock(ArtikelLeverancierDeletePort.class);
        UUID leverancierId = UUID.randomUUID();
        ManageLeverancierInMagazijnUnitOfWork manageLeverancierInMagazijnUnitOfWork = new ManageLeverancierInMagazijnUnitOfWork(null,null,List.of(artikelLeverancierDeletePort),null);

        //Act
        manageLeverancierInMagazijnUnitOfWork.deleteArtikelLeverancierInMagazijn(leverancierId);

        //Assert
        Mockito.verify(artikelLeverancierDeletePort).deleteArtikelLeverancier(leverancierUUIDCaptor.capture());
        UUID leverancierUUID = leverancierUUIDCaptor.getValue();
        assertEquals(leverancierId,leverancierUUID);
    }
}