package be.one16.barka.klant.core.order;

import be.one16.barka.klant.common.OrderType;
import be.one16.barka.klant.core.werkuur.DefaultCreateWerkuurUnitOfWork;
import be.one16.barka.klant.domain.Materiaal;
import be.one16.barka.klant.domain.Order;
import be.one16.barka.klant.domain.Werkuur;
import be.one16.barka.klant.ports.in.materiaal.MaterialenQuery;
import be.one16.barka.klant.ports.in.werkuur.CreateWerkuurCommand;
import be.one16.barka.klant.ports.in.werkuur.WerkurenQuery;
import be.one16.barka.klant.ports.out.order.LoadOrdersPort;
import be.one16.barka.klant.ports.out.werkuur.WerkuurCreatePort;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DefaultOrderQueryTest {

    @Test
    void retrieveOrderByIdTest() {

        UUID orderId = UUID.randomUUID();

        //Arrange
        LoadOrdersPort loadOrdersPort = Mockito.mock(LoadOrdersPort.class);
        MaterialenQuery materialenQuery = Mockito.mock(MaterialenQuery.class);
        WerkurenQuery werkurenQuery = Mockito.mock(WerkurenQuery.class);
        LocalDate date = LocalDate.of(2023, 1, 8);
        BigDecimal verkoopprijsvijftig = BigDecimal.valueOf(50);
        BigDecimal verkoopprijstien = BigDecimal.valueOf(10);
        BigDecimal totaalinclusnegentig = BigDecimal.valueOf(90);
        BigDecimal totaalinclustien = BigDecimal.valueOf(10);
        BigDecimal totaalexclusnegentig = BigDecimal.valueOf(74.38);
        BigDecimal totaalexclustien = BigDecimal.valueOf(8.26);
        BigDecimal btwnegentig = BigDecimal.valueOf(15.62);
        BigDecimal btwtien = BigDecimal.valueOf(1.74);
        BigDecimal resultinclus = BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal resultexclus = BigDecimal.valueOf(82.64);
        BigDecimal resultbtw = BigDecimal.valueOf(17.36);

        Materiaal fietszak = Materiaal.builder().artikelMerk("Agu").artikelCode("Agu21L").artikelOmschrijving("Agu fietstas 21L waterdicht").aantalArtikels(2).verkoopPrijsArtikel(verkoopprijsvijftig).korting(10).btwPerc(21).totaalExclusBtw(totaalexclusnegentig).totaalInclusBtw(totaalinclusnegentig).btwBedrag(btwnegentig).build();
        Materiaal fietsbel = Materiaal.builder().artikelMerk("Basil").artikelCode("Basilbelgrijs").artikelOmschrijving("Fietsbel basil grijs").aantalArtikels(1).verkoopPrijsArtikel(verkoopprijstien).btwPerc(21).totaalExclusBtw(totaalexclustien).totaalInclusBtw(totaalinclustien).btwBedrag(btwtien).build();
        List<Materiaal> materialen = new ArrayList<>();
        materialen.add(fietszak);
        materialen.add(fietsbel);

        Order order = Order.builder().orderId(orderId).datum(date).naam("Fietszakken en fietsbel").orderType(OrderType.VERKOOP).build();

        Mockito.when(loadOrdersPort.retrieveOrderById(orderId)).thenReturn(order);
        Mockito.when(materialenQuery.retrieveMaterialenOfOrder(orderId)).thenReturn(materialen);
        DefaultOrderQuery defaultOrderQuery = new DefaultOrderQuery(loadOrdersPort, materialenQuery, werkurenQuery);

        //Act
        Order orderPopulated = defaultOrderQuery.retrieveOrderById(orderId);

        assertEquals(orderPopulated.getTotaalExclusBtw(),resultexclus);
        assertEquals(orderPopulated.getTotaalInclusBtw(),resultinclus);
        assertEquals(orderPopulated.getBtwBedrag(),resultbtw);

    }

}