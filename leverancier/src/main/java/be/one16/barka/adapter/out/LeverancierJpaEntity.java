package be.one16.barka.adapter.out;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "leverancier.leveranciers")
public class LeverancierJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NaturalId
    @Type(type = "uuid-char")
    private UUID uuid = UUID.randomUUID();

    private String name;
    private String streetName;
    private int houseNumber;
    private String box;
    private int zipCode;
    private String city;
    private String country;
    private String phone;
    private String mobile;
    private String fax;
    private String email;
    private String btw;
    private String remarks;
}
