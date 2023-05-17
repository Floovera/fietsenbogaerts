package be.one16.barka.klant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class Werkuur {

    private UUID werkuurId;
    private Date datum;
    private double aantalUren;
    private double uurTarief;
    private int btwPerc;
    private double totaalExclusBtw;
    private double totaalInclusBtw;
    private double btwBedrag;
    private UUID verkoopId;

}
