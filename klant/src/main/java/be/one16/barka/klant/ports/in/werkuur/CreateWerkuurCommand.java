package be.one16.barka.klant.ports.in.werkuur;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public record CreateWerkuurCommand(LocalDate datum, double aantalUren, BigDecimal uurTarief, int btwPerc, UUID orderId){

    public CreateWerkuurCommand {

        if(datum == null){
            throw new IllegalArgumentException("Value for 'datum' can not be null");
        }

        if (aantalUren == 0.0) {
            throw new IllegalArgumentException("Value for 'aantal uren' can not be 0.0");
        }

        if (uurTarief.compareTo(new BigDecimal("0.00")) == 0) {
            throw new IllegalArgumentException("Value for 'uur tarief' can not be 0.0");
        }

        if (btwPerc != 6 && btwPerc != 21) {
            throw new IllegalArgumentException("Value for 'btw perc' should be 6 or 21");
        }

    }
}
