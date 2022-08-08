package be.one16.barka.leverancier.adapter.out;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "leverancier.leveranciers")
@Getter
@Setter
public class LeverancierJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NaturalId
    @Type(type = "uuid-char")
    private UUID uuid;

    private String naam;
    private String straat;
    private String huisnummer;
    private String bus;
    private String postcode;
    private String gemeente;
    private String land;
    private String telefoonnummer;
    private String mobiel;
    private String fax;
    private String email;
    private String btwNummer;
    private String opmerkingen;

}
