package be.one16.barka.klant.core.materiaal;
import be.one16.barka.klant.domain.Materiaal;
import be.one16.barka.klant.ports.in.materiaal.CreateMateriaalCommand;
import be.one16.barka.klant.ports.out.materiaal.MateriaalCreatePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DefaultCreateMateriaalUnitOfWorkTest {

    @Captor
    ArgumentCaptor<Materiaal> materiaalCaptor;

    @ParameterizedTest
    @MethodSource("valuesForCreateMateriaal")
    void createMateriaal(String artikelMerk, String artikelCode, String artikelOmschrijving, int aantalArtikels, BigDecimal verkoopPrijsArtikel, int korting, int btwPerc, BigDecimal totaalInclusBtw, BigDecimal totaalExclusBtw, BigDecimal btwBedrag){

        //Arrange
        MateriaalCreatePort materiaalCreatePort = Mockito.mock(MateriaalCreatePort.class);
        DefaultCreateMateriaalUnitOfWork createMateriaalUnitOfWork = new DefaultCreateMateriaalUnitOfWork(List.of(materiaalCreatePort));
        CreateMateriaalCommand createMateriaalCommand = new CreateMateriaalCommand(UUID.randomUUID(),artikelMerk,artikelCode,artikelOmschrijving,aantalArtikels,verkoopPrijsArtikel,korting,btwPerc,UUID.randomUUID());

        //Act
        createMateriaalUnitOfWork.createMateriaal(createMateriaalCommand);

        //Assert
        Mockito.verify(materiaalCreatePort).createMateriaal(materiaalCaptor.capture());
        Materiaal materiaal = materiaalCaptor.getValue();
        assertEquals(artikelMerk,materiaal.getArtikelMerk());
        assertEquals(artikelCode,materiaal.getArtikelCode());
        assertEquals(artikelOmschrijving,materiaal.getArtikelOmschrijving());
        assertEquals(aantalArtikels,materiaal.getAantalArtikels());
        assertEquals(verkoopPrijsArtikel,materiaal.getVerkoopPrijsArtikel());
        assertEquals(korting,materiaal.getKorting());
        assertEquals(btwPerc,materiaal.getBtwPerc());
        assertEquals(totaalInclusBtw,materiaal.getTotaalInclusBtw());
        assertEquals(totaalExclusBtw,materiaal.getTotaalExclusBtw());
        assertEquals(btwBedrag,materiaal.getBtwBedrag());

    }

    private static Stream<Arguments> valuesForCreateMateriaal() {
        BigDecimal verkoopprijs = BigDecimal.valueOf(50).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totaalinclus = BigDecimal.valueOf(90).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totaalexclus = BigDecimal.valueOf(74.38);
        BigDecimal btw = BigDecimal.valueOf(15.62);

        return Stream.of(
                Arguments.of("Bib","123456","Binnenband",2,verkoopprijs,10,21,totaalinclus,totaalexclus,btw)
        );
    }

    @Test
    void createMateriaalWhenArtikelMerkIsEmpty() {

        //Arrange
        MateriaalCreatePort materiaalCreatePort = Mockito.mock(MateriaalCreatePort.class);
        DefaultCreateMateriaalUnitOfWork createMateriaalUnitOfWork = new DefaultCreateMateriaalUnitOfWork(List.of(materiaalCreatePort));
        BigDecimal verkoopprijs = BigDecimal.valueOf(50);

        //Act and assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> createMateriaalUnitOfWork.createMateriaal(new CreateMateriaalCommand(UUID.randomUUID(),"","123456","Binnenband",2,verkoopprijs,10,6,UUID.randomUUID())));
        assertEquals("Value for 'artikelMerk' can not be null or empty",illegalArgumentException.getMessage());
    }
    @Test
    void createMateriaalWhenArtikelCodeIsEmpty() {

        //Arrange
        MateriaalCreatePort materiaalCreatePort = Mockito.mock(MateriaalCreatePort.class);
        DefaultCreateMateriaalUnitOfWork createMateriaalUnitOfWork = new DefaultCreateMateriaalUnitOfWork(List.of(materiaalCreatePort));
        BigDecimal verkoopprijs = BigDecimal.valueOf(50);

        //Act and assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> createMateriaalUnitOfWork.createMateriaal(new CreateMateriaalCommand(UUID.randomUUID(),"Bib","","Binnenband",2,verkoopprijs,10,6,UUID.randomUUID())));
        assertEquals("Value for 'artikelCode' can not be null or empty",illegalArgumentException.getMessage());
    }
    @Test
    void createMateriaalWhenArtikelOmschrijvingIsEmpty() {

        //Arrange
        MateriaalCreatePort materiaalCreatePort = Mockito.mock(MateriaalCreatePort.class);
        DefaultCreateMateriaalUnitOfWork createMateriaalUnitOfWork = new DefaultCreateMateriaalUnitOfWork(List.of(materiaalCreatePort));
        BigDecimal verkoopprijs = BigDecimal.valueOf(50);

        //Act and assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> createMateriaalUnitOfWork.createMateriaal(new CreateMateriaalCommand(UUID.randomUUID(),"Bib","123456","",2,verkoopprijs,10,6,UUID.randomUUID())));
        assertEquals("Value for 'artikelOmschrijving' can not be null or empty",illegalArgumentException.getMessage());
    }
    @Test
    void createMateriaalWhenAantalArtikelsIs0() {

        //Arrange
        MateriaalCreatePort materiaalCreatePort = Mockito.mock(MateriaalCreatePort.class);
        DefaultCreateMateriaalUnitOfWork createMateriaalUnitOfWork = new DefaultCreateMateriaalUnitOfWork(List.of(materiaalCreatePort));
        BigDecimal verkoopprijs = BigDecimal.valueOf(50);

        //Act and assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> createMateriaalUnitOfWork.createMateriaal(new CreateMateriaalCommand(UUID.randomUUID(),"Bib","123456","Binnenband",0,verkoopprijs,10,6,UUID.randomUUID())));
        assertEquals("Value for 'aantalArtikels' can not be 0",illegalArgumentException.getMessage());
    }
    @Test
    void createMateriaalWhenVerkoopPrijsArtikelIs0() {

        //Arrange
        MateriaalCreatePort materiaalCreatePort = Mockito.mock(MateriaalCreatePort.class);
        DefaultCreateMateriaalUnitOfWork createMateriaalUnitOfWork = new DefaultCreateMateriaalUnitOfWork(List.of(materiaalCreatePort));
        BigDecimal verkoopprijs = BigDecimal.valueOf(0);

        //Act and assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> createMateriaalUnitOfWork.createMateriaal(new CreateMateriaalCommand(UUID.randomUUID(),"Bib","123456","Binnenband",2,verkoopprijs,10,6,UUID.randomUUID())));
        assertEquals("Value for 'verkoopPrijsArtikel' can not be 0.0",illegalArgumentException.getMessage());
    }
    @Test
    void createMateriaalWhenBtwPercIs0() {

        //Arrange
        MateriaalCreatePort materiaalCreatePort = Mockito.mock(MateriaalCreatePort.class);
        DefaultCreateMateriaalUnitOfWork createMateriaalUnitOfWork = new DefaultCreateMateriaalUnitOfWork(List.of(materiaalCreatePort));
        BigDecimal verkoopprijs = BigDecimal.valueOf(50);

        //Act and assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> createMateriaalUnitOfWork.createMateriaal(new CreateMateriaalCommand(UUID.randomUUID(),"Bib","123456","Binnenband",2,verkoopprijs,10,0,UUID.randomUUID())));
        assertEquals("Value for 'btw perc' should be 6 or 21",illegalArgumentException.getMessage());
    }

}