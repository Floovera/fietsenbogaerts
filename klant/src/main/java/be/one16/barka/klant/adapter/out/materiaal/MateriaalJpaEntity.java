package be.one16.barka.klant.adapter.out.materiaal;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "klant.materialen")
@Getter
@Setter
public class MateriaalJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Type(type = "uuid-char")
    private UUID uuid;
    @Type(type = "uuid-char")
    private UUID artikeluuid;
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
    @Type(type = "uuid-char")
    private UUID verkoopuuid;

}