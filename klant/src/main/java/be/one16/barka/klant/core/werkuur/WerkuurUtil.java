package be.one16.barka.klant.core.werkuur;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class WerkuurUtil {

    protected static double calculateTotaalInclusBtw(double aantalUren, double uurTarief){

        BigDecimal hours = BigDecimal.valueOf(aantalUren);
        BigDecimal price = BigDecimal.valueOf(uurTarief);
        BigDecimal total = hours.multiply(price);

        double result = total.doubleValue();
        return result;
//
//        double totaalInclus =  aantalUren * uurTarief;
//        double totaalInclusRounded = round(totaalInclus);
//        return totaalInclusRounded;
    }

    protected static double calculateTotaalExclusBtw(double totaalInclusBtw, int btwPerc){
        BigDecimal totalinclus = BigDecimal.valueOf(totaalInclusBtw);
        BigDecimal vat = BigDecimal.valueOf(btwPerc);
        BigDecimal hundred = BigDecimal.valueOf(100);
        BigDecimal one = BigDecimal.valueOf(1);
        BigDecimal vatperc = vat.divide(hundred);
        BigDecimal topay = one.add(vatperc);
        BigDecimal totaalexclus = totalinclus.divide(topay,2, RoundingMode.HALF_UP);

        double result = totaalexclus.doubleValue();
        return result;

//        double totaalExclus =  totaalInclusBtw / (1+(btwPerc/100.0));
//        double totaalExlcusRounded = round(totaalExclus);
//        return totaalExlcusRounded;
    }

    protected static double calculateBtwBedrag(double totaalInclusBtw, double totaalExlcusBtw){

        BigDecimal totalinclus = BigDecimal.valueOf(totaalInclusBtw);
        BigDecimal totalexclus = BigDecimal.valueOf(totaalExlcusBtw);
        BigDecimal vatamount = totalinclus.subtract(totalexclus);

        double result = vatamount.doubleValue();
        return result;

//        double btwBedrag =  totaalInclusBtw - totaalExlcusBtw;
//        double btwBedragRounded = round(btwBedrag);
//        return btwBedragRounded;
    }

    protected static double round(double amountToRound){

        double roundOff = Math.round(amountToRound * 100.0) / 100.0;
        return roundOff;
    }
}
