package be.one16.barka.klant.core.werkuur;

import be.one16.barka.klant.domain.Werkuur;
import be.one16.barka.klant.ports.in.werkuur.CreateWerkuurCommand;
import be.one16.barka.klant.ports.in.werkuur.UpdateWerkuurCommand;
import be.one16.barka.klant.ports.out.werkuur.WerkuurCreatePort;
import be.one16.barka.klant.ports.out.werkuur.WerkuurUpdatePort;
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
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DefaultUpdateWerkuurUnitOfWorkTest {

    @Captor
    ArgumentCaptor<Werkuur> werkuurCaptor;

    @ParameterizedTest
    @MethodSource("valuesForUpdateWerkuur")
    void updateWerkuur(double aantalUren, double uurTarief, int btwPerc, double totaalInclusBtw, double totaalExclusBtw, double btwBedrag){

        //Arrange
        WerkuurUpdatePort werkuurUpdatePort = Mockito.mock(WerkuurUpdatePort.class);
        DefaultUpdateWerkuurUnitOfWork updateWerkuurUnitOfWork = new DefaultUpdateWerkuurUnitOfWork(List.of(werkuurUpdatePort));
        LocalDate date = LocalDate.of(2023, 1, 8);
        Werkuur werkuurToUpdate = new Werkuur(UUID.randomUUID(),date,0.3,50,21,15,12.4,2.6,UUID.randomUUID());
        UpdateWerkuurCommand updateWerkuurCommand = new UpdateWerkuurCommand(werkuurToUpdate.getWerkuurId(),date,aantalUren,uurTarief,btwPerc,werkuurToUpdate.getOrderId());


        //Act
        updateWerkuurUnitOfWork.updateWerkuur(updateWerkuurCommand);

        //Assert
        Mockito.verify(werkuurUpdatePort).updateWerkuur(werkuurCaptor.capture());
        Werkuur werkuur = werkuurCaptor.getValue();
        assertEquals(date,werkuur.getDatum());
        assertEquals(aantalUren,werkuur.getAantalUren());
        assertEquals(uurTarief,werkuur.getUurTarief());
        assertEquals(btwPerc,werkuur.getBtwPerc());
        assertEquals(totaalInclusBtw,werkuur.getTotaalInclusBtw());
        assertEquals(totaalExclusBtw,werkuur.getTotaalExclusBtw());
        assertEquals(btwBedrag,werkuur.getBtwBedrag());

    }

    private static Stream<Arguments> valuesForUpdateWerkuur() {
        return Stream.of(
                Arguments.of(0.3,50,21,15,12.4,2.6),
                Arguments.of(1,100,21,100,82.64,17.36)
        );
    }

    @Test
    void updateWerkuurWhenBtwPercIs0() {

        //Arrange
        WerkuurUpdatePort werkuurUpdatePort = Mockito.mock(WerkuurUpdatePort.class);
        DefaultUpdateWerkuurUnitOfWork updateWerkuurUnitOfWork = new DefaultUpdateWerkuurUnitOfWork(List.of(werkuurUpdatePort));
        LocalDate date = LocalDate.of(2023, 1, 8);
        Werkuur werkuurToUpdate = new Werkuur(UUID.randomUUID(),date,0.3,50,21,15,12.4,2.6,UUID.randomUUID());

        //Act and assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> updateWerkuurUnitOfWork.updateWerkuur(new UpdateWerkuurCommand(werkuurToUpdate.getWerkuurId(),date, 0.3, 50, 0, UUID.randomUUID())));
        assertEquals("Value for 'btw perc' should be 6 or 21",illegalArgumentException.getMessage());
    }

    @Test
    void updateWerkuurWhenValueForDateIsNull() {

        //Arrange
        WerkuurUpdatePort werkuurUpdatePort = Mockito.mock(WerkuurUpdatePort.class);
        DefaultUpdateWerkuurUnitOfWork updateWerkuurUnitOfWork = new DefaultUpdateWerkuurUnitOfWork(List.of(werkuurUpdatePort));
        LocalDate date = LocalDate.of(2023, 1, 8);
        Werkuur werkuurToUpdate = new Werkuur(UUID.randomUUID(),date,0.3,50,21,15,12.4,2.6,UUID.randomUUID());

        //Act and assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> updateWerkuurUnitOfWork.updateWerkuur(new UpdateWerkuurCommand(werkuurToUpdate.getWerkuurId(),null, 0.3, 50, 6, UUID.randomUUID())));
        assertEquals("Value for 'datum' can not be null",illegalArgumentException.getMessage());
    }

    @Test
    void updateWerkuurWhenUurTariefIs0() {

        //Arrange
        WerkuurUpdatePort werkuurUpdatePort = Mockito.mock(WerkuurUpdatePort.class);
        DefaultUpdateWerkuurUnitOfWork updateWerkuurUnitOfWork = new DefaultUpdateWerkuurUnitOfWork(List.of(werkuurUpdatePort));
        LocalDate date = LocalDate.of(2023, 1, 8);
        Werkuur werkuurToUpdate = new Werkuur(UUID.randomUUID(),date,0.3,50,21,15,12.4,2.6,UUID.randomUUID());

        //Act and assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> updateWerkuurUnitOfWork.updateWerkuur(new UpdateWerkuurCommand(werkuurToUpdate.getWerkuurId(),date, 0.3, 0, 21, UUID.randomUUID())));
        assertEquals("Value for 'uur tarief' can not be 0.0",illegalArgumentException.getMessage());
    }

    @Test
    void updateWerkuurWhenAantalUrenIs0() {

        //Arrange
        WerkuurUpdatePort werkuurUpdatePort = Mockito.mock(WerkuurUpdatePort.class);
        DefaultUpdateWerkuurUnitOfWork updateWerkuurUnitOfWork = new DefaultUpdateWerkuurUnitOfWork(List.of(werkuurUpdatePort));
        LocalDate date = LocalDate.of(2023, 1, 8);
        Werkuur werkuurToUpdate = new Werkuur(UUID.randomUUID(),date,0.3,50,21,15,12.4,2.6,UUID.randomUUID());

        //Act and assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> updateWerkuurUnitOfWork.updateWerkuur(new UpdateWerkuurCommand(werkuurToUpdate.getWerkuurId(),date, 0, 50, 21, UUID.randomUUID())));
        assertEquals("Value for 'aantal uren' can not be 0.0",illegalArgumentException.getMessage());
    }

}