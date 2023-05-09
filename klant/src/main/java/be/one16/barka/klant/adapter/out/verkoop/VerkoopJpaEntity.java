package be.one16.barka.klant.adapter.out.verkoop;

import be.one16.barka.klant.adapter.out.klant.KlantJpaEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

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
    private String naam;
    private String opmerkingen;
    @ManyToOne
    @JoinColumn(name = "klant_id")
    private KlantJpaEntity klant;

}
