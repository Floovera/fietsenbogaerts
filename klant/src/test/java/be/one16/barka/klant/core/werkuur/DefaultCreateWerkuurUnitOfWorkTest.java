package be.one16.barka.klant.core.werkuur;

import be.one16.barka.klant.domain.Werkuur;
import be.one16.barka.klant.ports.in.werkuur.CreateWerkuurCommand;
import be.one16.barka.klant.ports.out.werkuur.WerkuurCreatePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class DefaultCreateWerkuurUnitOfWorkTest {
    @Captor
    ArgumentCaptor<Werkuur> werkuurCaptor;

    @ParameterizedTest
    @MethodSource("valuesForCreateWerkuur")
    void createWerkuur(double aantalUren, double uurTarief, int btwPerc, double totaalInclusBtw, double totaalExclusBtw, double btwBedrag){

        //Arrange
        WerkuurCreatePort werkuurCreatePort = Mockito.mock(WerkuurCreatePort.class);
        DefaultCreateWerkuurUnitOfWork createWerkuurUnitOfWork = new DefaultCreateWerkuurUnitOfWork(List.of(werkuurCreatePort));
        LocalDate date = LocalDate.of(2023, 1, 8);
        CreateWerkuurCommand createWerkuurCommand = new CreateWerkuurCommand(date,aantalUren,uurTarief,btwPerc, UUID.randomUUID());

        //Act
        createWerkuurUnitOfWork.createWerkuur(createWerkuurCommand);

        //Assert
        Mockito.verify(werkuurCreatePort).createWerkuur(werkuurCaptor.capture());
        Werkuur werkuur = werkuurCaptor.getValue();
        assertEquals(date,werkuur.getDatum());
        assertEquals(aantalUren,werkuur.getAantalUren());
        assertEquals(uurTarief,werkuur.getUurTarief());
        assertEquals(btwPerc,werkuur.getBtwPerc());
        assertEquals(totaalInclusBtw,werkuur.getTotaalInclusBtw());
        assertEquals(totaalExclusBtw,werkuur.getTotaalExclusBtw());
        assertEquals(btwBedrag,werkuur.getBtwBedrag());

    }

    private static Stream<Arguments> valuesForCreateWerkuur() {
        return Stream.of(
                Arguments.of(0.3,50,21,15,12.4,2.6),
                Arguments.of(1,100,21,100,82.64,17.36)
        );
    }

    @Test
    void createWerkuurWhenBtwPercIs0() {

        //Arrange
        WerkuurCreatePort werkuurCreatePort = Mockito.mock(WerkuurCreatePort.class);
        DefaultCreateWerkuurUnitOfWork createWerkuurUnitOfWork = new DefaultCreateWerkuurUnitOfWork(List.of(werkuurCreatePort));
        LocalDate date = LocalDate.of(2023, 1, 8);

        //Act and assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> createWerkuurUnitOfWork.createWerkuur(new CreateWerkuurCommand(date, 0.3, 50, 0, UUID.randomUUID())));
        assertEquals("Value for 'btw perc' should be 6 or 21",illegalArgumentException.getMessage());
    }

    @Test
    void createWerkuurWhenValueForDateIsNull() {

        //Arrange
        WerkuurCreatePort werkuurCreatePort = Mockito.mock(WerkuurCreatePort.class);
        DefaultCreateWerkuurUnitOfWork createWerkuurUnitOfWork = new DefaultCreateWerkuurUnitOfWork(List.of(werkuurCreatePort));

        //Act and assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> createWerkuurUnitOfWork.createWerkuur(new CreateWerkuurCommand(null, 0.3, 50, 6, UUID.randomUUID())));
        assertEquals("Value for 'datum' can not be null",illegalArgumentException.getMessage());
    }

    @Test
    void createWerkuurWhenUurTariefIs0() {

        //Arrange
        WerkuurCreatePort werkuurCreatePort = Mockito.mock(WerkuurCreatePort.class);
        DefaultCreateWerkuurUnitOfWork createWerkuurUnitOfWork = new DefaultCreateWerkuurUnitOfWork(List.of(werkuurCreatePort));
        LocalDate date = LocalDate.of(2023, 1, 8);
        //Act and assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> createWerkuurUnitOfWork.createWerkuur(new CreateWerkuurCommand(date, 0.3, 0, 6, UUID.randomUUID())));
        assertEquals("Value for 'uur tarief' can not be 0.0",illegalArgumentException.getMessage());
    }

    @Test
    void createWerkuurWhenAantalUrenIs0() {

        //Arrange
        WerkuurCreatePort werkuurCreatePort = Mockito.mock(WerkuurCreatePort.class);
        DefaultCreateWerkuurUnitOfWork createWerkuurUnitOfWork = new DefaultCreateWerkuurUnitOfWork(List.of(werkuurCreatePort));
        LocalDate date = LocalDate.of(2023, 1, 8);
        //Act and assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> createWerkuurUnitOfWork.createWerkuur(new CreateWerkuurCommand(date, 0, 50, 6, UUID.randomUUID())));
        assertEquals("Value for 'aantal uren' can not be 0.0",illegalArgumentException.getMessage());
    }



}