package be.one16.barka.klant.domain;

import be.one16.barka.klant.common.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class Order {

    private UUID orderId;
    private OrderType orderType;
    private String naam;
    private String opmerkingen;
    private LocalDate datum;
    private UUID klantId;

    private String reparatieNummer;
    private String orderNummer;

    private List<Werkuur> werkuren;
    private List<Materiaal> materialen;
    public double getTotaalExclusBtw(){
        double totaalExclusBtw = 0;
        double materialenTotaalExclusBtw = 0;
        double werkurenTotaalExclusBtw = 0;

        for (Materiaal materiaal : materialen) {
            materialenTotaalExclusBtw = materialenTotaalExclusBtw + materiaal.getTotaalExclusBtw();
        }

        for (Werkuur werkuur : werkuren) {
            werkurenTotaalExclusBtw = werkurenTotaalExclusBtw + werkuur.getTotaalExclusBtw();
        }

        totaalExclusBtw = materialenTotaalExclusBtw + werkurenTotaalExclusBtw;

        return  round(totaalExclusBtw);
    }

    public double getTotaalInclusBtw(){
        double totaalInclusBtw = 0;
        double materialenTotaalInclusBtw = 0;
        double werkurenTotaalInclusBtw = 0;

        for (Materiaal materiaal : materialen) {
            materialenTotaalInclusBtw = materialenTotaalInclusBtw + materiaal.getTotaalInclusBtw();
        }

        for (Werkuur werkuur : werkuren) {
            werkurenTotaalInclusBtw = werkurenTotaalInclusBtw + werkuur.getTotaalInclusBtw();
        }

        totaalInclusBtw = materialenTotaalInclusBtw + werkurenTotaalInclusBtw;

        return  round(totaalInclusBtw);
    }

    public double getBtwBedrag(){
        double totaalBtwBedrag = 0;
        double materialenBtwBedrag = 0;
        double werkurenBtwBedrag = 0;

        for (Materiaal materiaal : materialen) {
            materialenBtwBedrag = materialenBtwBedrag + materiaal.getBtwBedrag();
        }

        for (Werkuur werkuur : werkuren) {
            werkurenBtwBedrag = werkurenBtwBedrag + werkuur.getBtwBedrag();
        }

        totaalBtwBedrag = materialenBtwBedrag + werkurenBtwBedrag;

        return round(totaalBtwBedrag);}

    public double round(double amountToRound){

        double roundOff = Math.round(amountToRound * 100.0) / 100.0;
        return roundOff;
    }

    public boolean checkSwitchType(OrderType newOrderType){
        boolean switchOK = true;

        if(newOrderType==OrderType.VERKOOPP){
            switchOK = false;
        }
        if(orderType==OrderType.FACTUUR){
            switchOK = false;
        }
        return switchOK;
    }

    public void switchOrderType(OrderType newOrderType){

    }

    public void setOrderNummer(int lastOrderNummer) {
        int lastOrderPlusOne = lastOrderNummer + 1;
        int year = datum.getYear();
        orderNummer = Integer.toString(year) + "/" + Integer.toString(lastOrderPlusOne) ;
    }
}
