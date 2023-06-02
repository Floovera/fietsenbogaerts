package be.one16.barka.klant.domain;

import be.one16.barka.klant.common.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public BigDecimal getTotaalExclusBtw(){
        BigDecimal totaalExclusBtw = BigDecimal.valueOf(0);
        BigDecimal materialenTotaalExclusBtw = BigDecimal.valueOf(0);
        BigDecimal werkurenTotaalExclusBtw = BigDecimal.valueOf(0);

        for (Materiaal materiaal : materialen) {
            BigDecimal totalmaterial = materiaal.getTotaalExclusBtw();
            materialenTotaalExclusBtw = materialenTotaalExclusBtw.add(totalmaterial);
        }

        for (Werkuur werkuur : werkuren) {
            BigDecimal totallabour = werkuur.getTotaalExclusBtw();
            werkurenTotaalExclusBtw = werkurenTotaalExclusBtw.add(totallabour);
        }

        totaalExclusBtw = materialenTotaalExclusBtw.add(werkurenTotaalExclusBtw);
        BigDecimal rounded = totaalExclusBtw.setScale(2, RoundingMode.HALF_EVEN);
        return rounded;

    }

    public BigDecimal getTotaalInclusBtw(){
        BigDecimal totaalInclusBtw = BigDecimal.valueOf(0);
        BigDecimal materialenTotaalInclusBtw = BigDecimal.valueOf(0);
        BigDecimal werkurenTotaalInclusBtw = BigDecimal.valueOf(0);

        for (Materiaal materiaal : materialen) {
            BigDecimal totalmaterial = materiaal.getTotaalInclusBtw();
            materialenTotaalInclusBtw = materialenTotaalInclusBtw.add(totalmaterial);
        }

        for (Werkuur werkuur : werkuren) {
            BigDecimal totallabour = werkuur.getTotaalInclusBtw();
            werkurenTotaalInclusBtw = werkurenTotaalInclusBtw.add(totallabour);
        }

        totaalInclusBtw = materialenTotaalInclusBtw.add(werkurenTotaalInclusBtw);
        BigDecimal rounded = totaalInclusBtw.setScale(2, RoundingMode.HALF_EVEN);
        return  rounded;
    }

    public BigDecimal getBtwBedrag(){
        BigDecimal totaalBtwBedrag = BigDecimal.valueOf(0);
        BigDecimal materialenBtwBedrag = BigDecimal.valueOf(0);
        BigDecimal werkurenBtwBedrag = BigDecimal.valueOf(0);

        for (Materiaal materiaal : materialen) {
            BigDecimal vat = materiaal.getBtwBedrag();
            materialenBtwBedrag = materialenBtwBedrag.add(vat);
        }

        for (Werkuur werkuur : werkuren) {
            BigDecimal vat = werkuur.getBtwBedrag();
            werkurenBtwBedrag = werkurenBtwBedrag.add(vat);
        }

        totaalBtwBedrag = materialenBtwBedrag.add(werkurenBtwBedrag);
        BigDecimal rounded = totaalBtwBedrag.setScale(2, RoundingMode.HALF_EVEN);
        return rounded;
    }

    public boolean checkSwitchType(OrderType newOrderType){
        boolean switchOK = true;

        if(newOrderType==OrderType.VERKOOP){
            switchOK = false;
        }
        if(orderType==OrderType.FACTUUR){
            switchOK = false;
        }
        return switchOK;
    }

    public void setOrderNummer(int lastOrderNummer) {
        int lastOrderPlusOne = lastOrderNummer + 1;
        int year = datum.getYear();
        orderNummer = Integer.toString(year) + "/" + Integer.toString(lastOrderPlusOne) ;
    }
}
