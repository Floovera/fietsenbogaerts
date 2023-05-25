package be.one16.barka.klant.domain;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class Materiaal {

    private UUID materiaalId;
    private String artikelMerk;
    private String artikelCode;
    private String artikelOmschrijving;
    private int aantalArtikels;
    private double verkoopPrijsArtikel;
    private int korting;
    private int btwPerc;
    private double totaalExclusBtw;
    private double totaalInclusBtw;
    private double btwBedrag;
    private UUID verkoopId;

}
