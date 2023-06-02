package be.one16.barka.klant.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class Materiaal {

    private UUID materiaalId;
    private UUID artikelId;
    private String artikelMerk;
    private String artikelCode;
    private String artikelOmschrijving;
    private int aantalArtikels;
    private BigDecimal verkoopPrijsArtikel;
    private int korting;
    private int btwPerc;
    private BigDecimal totaalExclusBtw;
    private BigDecimal totaalInclusBtw;
    private BigDecimal btwBedrag;
    private UUID orderId;

}
