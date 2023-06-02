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

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
    void updateWerkuur(double aantalUren, BigDecimal uurTarief, int btwPerc, BigDecimal totaalInclusBtw, BigDecimal totaalExclusBtw, BigDecimal btwBedrag){

        //Arrange
        WerkuurUpdatePort werkuurUpdatePort = Mockito.mock(WerkuurUpdatePort.class);
        DefaultUpdateWerkuurUnitOfWork updateWerkuurUnitOfWork = new DefaultUpdateWerkuurUnitOfWork(List.of(werkuurUpdatePort));
        LocalDate date = LocalDate.of(2023, 1, 8);
        BigDecimal uurtarief = BigDecimal.valueOf(50);
        BigDecimal inclus = BigDecimal.valueOf(15);
        BigDecimal exclus = BigDecimal.valueOf(12.4);
        BigDecimal btw = BigDecimal.valueOf(2.6);
        Werkuur werkuurToUpdate = new Werkuur(UUID.randomUUID(),date,0.3,uurtarief,21,inclus,exclus,btw,UUID.randomUUID());
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
        BigDecimal uurtarief1 = BigDecimal.valueOf(50).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal uurtarief2 = BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal inclus1 = BigDecimal.valueOf(15).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal inclus2 = BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal exclus1 = BigDecimal.valueOf(12.4).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal exclus2 = BigDecimal.valueOf(82.64).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal btw1 = BigDecimal.valueOf(2.6).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal btw2 = BigDecimal.valueOf(17.36).setScale(2, RoundingMode.HALF_EVEN);
        return Stream.of(
                Arguments.of(0.3,uurtarief1,21,inclus1,exclus1,btw1),
                Arguments.of(1,uurtarief2,21,inclus2,exclus2,btw2)
        );
    }

    @Test
    void updateWerkuurWhenBtwPercIs0() {

        //Arrange
        WerkuurUpdatePort werkuurUpdatePort = Mockito.mock(WerkuurUpdatePort.class);
        DefaultUpdateWerkuurUnitOfWork updateWerkuurUnitOfWork = new DefaultUpdateWerkuurUnitOfWork(List.of(werkuurUpdatePort));
        LocalDate date = LocalDate.of(2023, 1, 8);
        BigDecimal uurtarief = BigDecimal.valueOf(50);
        BigDecimal inclus = BigDecimal.valueOf(15);
        BigDecimal exclus = BigDecimal.valueOf(12.4);
        BigDecimal btw = BigDecimal.valueOf(2.6);
        Werkuur werkuurToUpdate = new Werkuur(UUID.randomUUID(),date,0.3,uurtarief,21,inclus,exclus,btw,UUID.randomUUID());

        //Act and assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> updateWerkuurUnitOfWork.updateWerkuur(new UpdateWerkuurCommand(werkuurToUpdate.getWerkuurId(),date, 0.3, uurtarief, 0, UUID.randomUUID())));
        assertEquals("Value for 'btw perc' should be 6 or 21",illegalArgumentException.getMessage());
    }

    @Test
    void updateWerkuurWhenValueForDateIsNull() {

        //Arrange
        WerkuurUpdatePort werkuurUpdatePort = Mockito.mock(WerkuurUpdatePort.class);
        DefaultUpdateWerkuurUnitOfWork updateWerkuurUnitOfWork = new DefaultUpdateWerkuurUnitOfWork(List.of(werkuurUpdatePort));
        LocalDate date = LocalDate.of(2023, 1, 8);
        BigDecimal uurtarief = BigDecimal.valueOf(50);
        BigDecimal inclus = BigDecimal.valueOf(15);
        BigDecimal exclus = BigDecimal.valueOf(12.4);
        BigDecimal btw = BigDecimal.valueOf(2.6);
        Werkuur werkuurToUpdate = new Werkuur(UUID.randomUUID(),date,0.3,uurtarief,21,inclus,exclus,btw,UUID.randomUUID());

        //Act and assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> updateWerkuurUnitOfWork.updateWerkuur(new UpdateWerkuurCommand(werkuurToUpdate.getWerkuurId(),null, 0.3, uurtarief, 6, UUID.randomUUID())));
        assertEquals("Value for 'datum' can not be null",illegalArgumentException.getMessage());
    }

    @Test
    void updateWerkuurWhenUurTariefIs0() {

        //Arrange
        WerkuurUpdatePort werkuurUpdatePort = Mockito.mock(WerkuurUpdatePort.class);
        DefaultUpdateWerkuurUnitOfWork updateWerkuurUnitOfWork = new DefaultUpdateWerkuurUnitOfWork(List.of(werkuurUpdatePort));
        LocalDate date = LocalDate.of(2023, 1, 8);
        BigDecimal uurtarief = BigDecimal.valueOf(50);
        BigDecimal nieuwuurtarief = BigDecimal.valueOf(0);
        BigDecimal inclus = BigDecimal.valueOf(15);
        BigDecimal exclus = BigDecimal.valueOf(12.4);
        BigDecimal btw = BigDecimal.valueOf(2.6);
        Werkuur werkuurToUpdate = new Werkuur(UUID.randomUUID(),date,0.3,uurtarief,21,inclus,exclus,btw,UUID.randomUUID());

        //Act and assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> updateWerkuurUnitOfWork.updateWerkuur(new UpdateWerkuurCommand(werkuurToUpdate.getWerkuurId(),date, 0.3, nieuwuurtarief, 21, UUID.randomUUID())));
        assertEquals("Value for 'uur tarief' can not be 0.0",illegalArgumentException.getMessage());
    }

    @Test
    void updateWerkuurWhenAantalUrenIs0() {

        //Arrange
        WerkuurUpdatePort werkuurUpdatePort = Mockito.mock(WerkuurUpdatePort.class);
        DefaultUpdateWerkuurUnitOfWork updateWerkuurUnitOfWork = new DefaultUpdateWerkuurUnitOfWork(List.of(werkuurUpdatePort));
        LocalDate date = LocalDate.of(2023, 1, 8);
        BigDecimal uurtarief = BigDecimal.valueOf(50);
        BigDecimal inclus = BigDecimal.valueOf(15);
        BigDecimal exclus = BigDecimal.valueOf(12.4);
        BigDecimal btw = BigDecimal.valueOf(2.6);
        Werkuur werkuurToUpdate = new Werkuur(UUID.randomUUID(),date,0.3,uurtarief,21,inclus,exclus,btw,UUID.randomUUID());

        //Act and assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> updateWerkuurUnitOfWork.updateWerkuur(new UpdateWerkuurCommand(werkuurToUpdate.getWerkuurId(),date, 0, uurtarief, 21, UUID.randomUUID())));
        assertEquals("Value for 'aantal uren' can not be 0.0",illegalArgumentException.getMessage());
    }

}