package be.one16.barka.klant.core.werkuur;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class WerkuurUtil {

    protected static BigDecimal calculateTotaalInclusBtw(double aantalUren, BigDecimal uurTarief){

        BigDecimal hours = BigDecimal.valueOf(aantalUren);
        BigDecimal price = uurTarief;
        BigDecimal total = hours.multiply(price);
        BigDecimal rounded = total.setScale(2, RoundingMode.HALF_EVEN);
        return rounded;
    }

    protected static BigDecimal calculateTotaalExclusBtw(BigDecimal totaalInclusBtw, int btwPerc){
        BigDecimal totalinclus = totaalInclusBtw;
        BigDecimal vat = BigDecimal.valueOf(btwPerc);
        BigDecimal hundred = BigDecimal.valueOf(100);
        BigDecimal one = BigDecimal.valueOf(1);
        BigDecimal vatperc = vat.divide(hundred);
        BigDecimal topay = one.add(vatperc);
        BigDecimal totalexclus = totalinclus.divide(topay,2, RoundingMode.HALF_UP);
        return totalexclus;
    }

    protected static BigDecimal calculateBtwBedrag(BigDecimal totaalInclusBtw, BigDecimal totaalExlcusBtw){

        BigDecimal totalinclus = totaalInclusBtw;
        BigDecimal totalexclus = totaalExlcusBtw;
        BigDecimal vatamount = totalinclus.subtract(totalexclus);
        BigDecimal rounded = vatamount.setScale(2, RoundingMode.HALF_EVEN);
        return rounded;
    }

}
