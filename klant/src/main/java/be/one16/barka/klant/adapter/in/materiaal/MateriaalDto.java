package be.one16.barka.klant.adapter.in.materiaal;
import lombok.Data;
import java.util.UUID;

@Data
public class MateriaalDto {

    private UUID materiaalId;
    private UUID artikelId;
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

}
