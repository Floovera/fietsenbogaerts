package be.one16.barka.klant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class Werkuur {

    private UUID werkuurId;
    private LocalDate datum;
    private double aantalUren;
    private BigDecimal uurTarief;
    private int btwPerc;
    private BigDecimal totaalExclusBtw;
    private BigDecimal totaalInclusBtw;
    private BigDecimal btwBedrag;
    private UUID orderId;

}
