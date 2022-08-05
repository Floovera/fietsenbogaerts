package be.one16.barka.klant.adapter.out;

import be.one16.barka.klant.common.KlantType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "klant.klanten")
@Getter
@Setter
public class KlantJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Type(type = "uuid-char")
    private UUID uuid;

    private String naam;

    @Enumerated(EnumType.STRING)
    private KlantType klantType;

    private String straat;
    private String huisnummer;
    private String bus;
    private String postcode;
    private String gemeente;
    private String land;
    private String telefoonnummer;
    private String mobiel;
    private String email;
    private String btwNummer;
    private String opmerkingen;

}
