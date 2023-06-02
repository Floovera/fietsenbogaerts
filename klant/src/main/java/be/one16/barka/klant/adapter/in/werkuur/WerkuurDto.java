package be.one16.barka.klant.adapter.in.werkuur;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class WerkuurDto {

    private UUID werkuurId;
    // als je dit object probeert te creeeren uit json gebruik je deze deserializer om het object op te bouwen
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate datum;
    private double aantalUren;
    private BigDecimal uurTarief;
    private int btwPerc;
    private BigDecimal totaalExclusBtw;
    private BigDecimal totaalInclusBtw;
    private BigDecimal btwBedrag;
    private UUID orderId;

}
