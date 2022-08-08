package be.one16.barka.magazijn.adapters.out;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "magazijn.artikels")
@Getter
@Setter
public class ArtikelJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Type(type = "uuid-char")
    private UUID uuid;

    private String merk;

    @Column(unique = true)
    private String code;

    private String omschrijving;
    private Integer aantalInStock;
    private Integer minimumInStock;
    @Column(scale = 2)
    private BigDecimal aankoopPrijs;
    @Column(scale = 2)
    private BigDecimal verkoopPrijs;
    @Column(scale = 2)
    private BigDecimal actuelePrijs;

    @ManyToOne
    @JoinColumn(name = "leverancier_id")
    private ArtikelLeverancierJpaEntity leverancier;

}
