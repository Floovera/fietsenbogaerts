package be.one16.barka.klant.adapter.out.order;

import be.one16.barka.klant.common.OrderType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import java.time.LocalDate;
import javax.persistence.*;
import java.time.Year;
import java.util.UUID;

@Entity
@Table(name = "klant.orders")
@Getter
@Setter
public class OrderJpaEntity {

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
    private int jaar;
    @Type(type = "uuid-char")
    private UUID klantId;

    private String reparatieNummer;
    private String orderNummer;

    private int sequence;

}
