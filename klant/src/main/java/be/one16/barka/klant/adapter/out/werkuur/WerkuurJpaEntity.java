package be.one16.barka.klant.adapter.out.werkuur;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "klant.werkuren")
@Getter
@Setter
public class WerkuurJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Type(type = "uuid-char")
    private UUID uuid;
    private LocalDate datum;
    private double aantalUren;
    private BigDecimal uurTarief;
    private int btwPerc;
    private BigDecimal totaalExclusBtw;
    private BigDecimal totaalInclusBtw;
    private BigDecimal btwBedrag;
    @Type(type = "uuid-char")
    private UUID orderuuid;

}
