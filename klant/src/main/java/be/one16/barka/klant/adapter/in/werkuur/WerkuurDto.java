package be.one16.barka.klant.adapter.in.werkuur;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
public class WerkuurDto {

    private UUID werkuurId;
    // to do: LocalDate van maken
    // als je dit object probeert te creeeren uit json gebruik je deze deserializer om het object op te bouwen
    // @JsonDeserialize(using = LocalDateDeserializer.class)
    private Date datum;
    private double aantalUren;
    private double uurTarief;
    private int btwPerc;
    private double totaalExclusBtw;
    private double totaalInclusBtw;
    private double btwBedrag;
    private UUID verkoopId;

}
