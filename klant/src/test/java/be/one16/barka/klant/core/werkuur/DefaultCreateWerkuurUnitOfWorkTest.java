package be.one16.barka.klant.core.werkuur;

import be.one16.barka.klant.domain.Werkuur;
import be.one16.barka.klant.ports.in.werkuur.CreateWerkuurCommand;
import be.one16.barka.klant.ports.in.werkuur.CreateWerkuurUnitOfWork;
import be.one16.barka.klant.ports.out.werkuur.WerkuurCreatePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DefaultCreateWerkuurUnitOfWorkTest {
    @Captor
    ArgumentCaptor<Werkuur> werkuurCaptor;

    @Test
    void createWerkuur(){
        //Arrange
        WerkuurCreatePort werkuurCreatePort = Mockito.mock(WerkuurCreatePort.class);
        DefaultCreateWerkuurUnitOfWork createWerkuurUnitOfWork = new DefaultCreateWerkuurUnitOfWork(List.of(werkuurCreatePort));
        LocalDate date = LocalDate.of(2023, 1, 8);
        CreateWerkuurCommand createWerkuurCommand = new CreateWerkuurCommand(date,0.3,50,21,0.0,0.0,0.0, UUID.randomUUID());

        //Act
        createWerkuurUnitOfWork.createWerkuur(createWerkuurCommand);

        //Assert
        Mockito.verify(werkuurCreatePort).createWerkuur(werkuurCaptor.capture());
        Werkuur werkuur = werkuurCaptor.getValue();
        assertEquals(date,werkuur.getDatum());
        assertEquals(0.3,werkuur.getAantalUren());
        assertEquals(50,werkuur.getUurTarief());
        assertEquals(21,werkuur.getBtwPerc());
        assertEquals(15,werkuur.getTotaalInclusBtw());
        assertEquals(12.4,werkuur.getTotaalExclusBtw());
        assertEquals(2.6,werkuur.getBtwBedrag());

    }





}