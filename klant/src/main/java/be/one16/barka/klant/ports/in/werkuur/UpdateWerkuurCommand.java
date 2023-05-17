package be.one16.barka.klant.ports.in.werkuur;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public record UpdateWerkuurCommand(UUID werkuurId, LocalDate datum, double aantalUren, double uurTarief, int btwPerc, double totaalExclusBtw, double totaalInclusBtw, double btwBedrag, UUID verkoopId){

    public UpdateWerkuurCommand {


        if (aantalUren == 0.0) {
            throw new IllegalArgumentException("Value for 'aantal uren' can not be 0.0");
        }

        if (uurTarief == 0.0) {
            throw new IllegalArgumentException("Value for 'uur tarief' can not be null");
        }

        if (btwPerc != 6 && btwPerc != 21) {
            throw new IllegalArgumentException("Value for 'btw perc' should be 6 or 21");
        }

    }
}
