package be.one16.barka.magazijn.adapters.out;

import be.one16.barka.magazijn.common.StatusLeverancier;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "magazijn.leveranciers")
@Getter
@Setter
public class ArtikelLeverancierJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Type(type = "uuid-char")
    private UUID uuid;
    private String naam;
    @Enumerated(EnumType.STRING)
    private StatusLeverancier status;

    @OneToMany(mappedBy = "leverancier")
    private Set<ArtikelJpaEntity> artikels;

}
