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
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

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
        CreateWerkuurCommand createWerkuurCommand = new CreateWerkuurCommand(date,aantalUren,uurTarief,btwPerc,0.0,0.0,0.0, UUID.randomUUID());

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





}