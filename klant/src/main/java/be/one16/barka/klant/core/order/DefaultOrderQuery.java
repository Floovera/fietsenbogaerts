package be.one16.barka.klant.core.order;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.klant.domain.Materiaal;
import be.one16.barka.klant.domain.Order;
import be.one16.barka.klant.domain.Werkuur;
import be.one16.barka.klant.ports.in.materiaal.MaterialenQuery;
import be.one16.barka.klant.ports.in.order.RetrieveOrderFilterAndSortCommand;
import be.one16.barka.klant.ports.in.order.OrderQuery;
import be.one16.barka.klant.ports.in.werkuur.WerkurenQuery;
import be.one16.barka.klant.ports.out.order.LoadOrdersPort;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

@UnitOfWork
public class DefaultOrderQuery implements OrderQuery {

    private final LoadOrdersPort loadOrdersPort;
    private final MaterialenQuery materialenQuery;
    private final WerkurenQuery werkurenQuery;

    public DefaultOrderQuery(LoadOrdersPort loadOrdersPort, MaterialenQuery materialenQuery, WerkurenQuery werkurenQuery) {
        this.loadOrdersPort = loadOrdersPort;
        this.materialenQuery = materialenQuery;
        this.werkurenQuery = werkurenQuery;
    }

    @Override
    public Order retrieveOrderById(UUID id) {
        Order order = loadOrdersPort.retrieveOrderById(id);
        List<Materiaal> materialen = materialenQuery.retrieveMaterialenOfOrder(id);
        List<Werkuur> werkuren = werkurenQuery.retrieveWerkurenOfOrder(id);

        double totaalInclusBtw = calculateTotaalInclusBtw(materialen, werkuren);
        double totaalExlcusBtw = calculateTotaalExclusBtw(materialen,werkuren);
        double btwBedrag = calculateBtwBedrag(materialen,werkuren);

        order.setTotaalExclusBtw(totaalExlcusBtw);
        order.setTotaalInclusBtw(totaalInclusBtw);
        order.setBtwBedrag(btwBedrag);

        return order;
        }


    @Override
    public Page<Order> retrieveOrdersByFilterAndSort(RetrieveOrderFilterAndSortCommand retrieveOrderFilterAndSortCommand) {
        return loadOrdersPort.retrieveOrdersByFilterAndSort(retrieveOrderFilterAndSortCommand.naam(), retrieveOrderFilterAndSortCommand.pageable());
    }


    private double calculateTotaalExclusBtw(List<Materiaal> materialen, List<Werkuur> werkuren){
        double totaalExclusBtw = 0;
        double materialenTotaalExclusBtw = 0;
        double werkurenTotaalExclusBtw = 0;

        for (int i = 0; i < materialen.size(); i++) {
            materialenTotaalExclusBtw = materialenTotaalExclusBtw + materialen.get(i).getTotaalExclusBtw();}

        for (int i = 0; i < werkuren.size(); i++) {
            werkurenTotaalExclusBtw = werkurenTotaalExclusBtw + werkuren.get(i).getTotaalExclusBtw();}

        totaalExclusBtw = materialenTotaalExclusBtw + werkurenTotaalExclusBtw;

        return  round(totaalExclusBtw);
    }

    private double calculateTotaalInclusBtw(List<Materiaal> materialen, List<Werkuur> werkuren){
        double totaalInclusBtw = 0;
        double materialenTotaalInclusBtw = 0;
        double werkurenTotaalInclusBtw = 0;

        for (int i = 0; i < materialen.size(); i++) {
            materialenTotaalInclusBtw = materialenTotaalInclusBtw + materialen.get(i).getTotaalInclusBtw();}

        for (int i = 0; i < werkuren.size(); i++) {
            werkurenTotaalInclusBtw = werkurenTotaalInclusBtw + werkuren.get(i).getTotaalInclusBtw();
        }

        totaalInclusBtw = materialenTotaalInclusBtw + werkurenTotaalInclusBtw;

        return  round(totaalInclusBtw);
    }

    private double calculateBtwBedrag(List<Materiaal> materialen, List<Werkuur> werkuren){
        double totaalBtwBedrag = 0;
        double materialenBtwBedrag = 0;
        double werkurenBtwBedrag = 0;

        for (int i = 0; i < materialen.size(); i++) {
            materialenBtwBedrag = materialenBtwBedrag + materialen.get(i).getBtwBedrag();}

        for (int i = 0; i < werkuren.size(); i++) {
            werkurenBtwBedrag = werkurenBtwBedrag + werkuren.get(i).getBtwBedrag();
        }

        totaalBtwBedrag = materialenBtwBedrag + werkurenBtwBedrag;

        return round(totaalBtwBedrag);}

        private double round(double amountToRound){

        double roundOff = Math.round(amountToRound * 100.0) / 100.0;
        return roundOff;
        }
}
