package be.one16.barka.magazijn.adapters.out;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

@Entity
@Table(name = "artikels", schema = "magazijn")
public class ArtikelJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NaturalId
    @Type(type = "uuid-char")
    private UUID uuid;

    private String merk;
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

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Integer getAantalInStock() {
        return aantalInStock;
    }

    public void setAantalInStock(Integer aantalInStock) {
        this.aantalInStock = aantalInStock;
    }

    public Integer getMinimumInStock() {
        return minimumInStock;
    }

    public void setMinimumInStock(Integer minimumInStock) {
        this.minimumInStock = minimumInStock;
    }

    public BigDecimal getAankoopPrijs() {
        return aankoopPrijs;
    }

    public void setAankoopPrijs(BigDecimal aankoopPrijs) {
        this.aankoopPrijs = aankoopPrijs;
    }

    public BigDecimal getVerkoopPrijs() {
        return verkoopPrijs;
    }

    public void setVerkoopPrijs(BigDecimal verkoopPrijs) {
        this.verkoopPrijs = verkoopPrijs;
    }

    public BigDecimal getActuelePrijs() {
        return actuelePrijs;
    }

    public void setActuelePrijs(BigDecimal actuelePrijs) {
        this.actuelePrijs = actuelePrijs;
    }
}
