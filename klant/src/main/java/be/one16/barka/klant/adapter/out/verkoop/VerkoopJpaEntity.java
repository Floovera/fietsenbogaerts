package be.one16.barka.klant.adapter.out.verkoop;

import be.one16.barka.klant.adapter.out.klant.KlantJpaEntity;
import be.one16.barka.klant.common.OrderType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import java.time.LocalDate;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "klant.verkopen")
@Getter
@Setter
public class VerkoopJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Type(type = "uuid-char")
    private UUID uuid;
    @Enumerated(EnumType.STRING)
    private OrderType orderType;
    private String naam;
    private String opmerkingen;
    private LocalDate datum;
    @Type(type = "uuid-char")
    private UUID klantId;

    private String reparatieNummer;
    private String orderNummer;

}
