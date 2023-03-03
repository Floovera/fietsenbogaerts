package be.one16.barka.leverancier.adapter.out;

import be.one16.barka.leverancier.common.ContactMethode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "leverancier.contacten")
@Getter
@Setter
public class ContactJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NaturalId
    @Type(type = "uuid-char")
    private UUID uuid;

    private String naam;
    private String onderwerp;
    private ContactMethode contactMethode;
    private String gegevens;

    @ManyToOne
    @JoinColumn(name = "leverancier_id", nullable = false)
    private LeverancierJpaEntity leverancier;


}
