package be.one16.barka.magazijn.ports.in;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class UpdateArtikelCommand {

    private UUID artikelId;

    private final String merk;
    private final String code;
    private final String omschrijving;
    private final UUID leverancierId;
    private final int aantalInStock;
    private final int minimumInStock;
    private final BigDecimal aankoopPrijs;
    private final BigDecimal verkoopPrijs;
    private final BigDecimal actuelePrijs;

    public UpdateArtikelCommand(String merk, String code, String omschrijving, UUID leverancierId, int aantalInStock, int minimumInStock, BigDecimal aankoopPrijs, BigDecimal verkoopPrijs, BigDecimal actuelePrijs) {
        if (StringUtils.isEmpty(merk) || StringUtils.isEmpty(code) || StringUtils.isEmpty(omschrijving)){
            throw new IllegalArgumentException("Value for 'merk', 'code' or 'omschrijving' can not be null or empty");
        }

        if (leverancierId == null) {
            throw new IllegalArgumentException("Value for 'leverancierId' can not be null or empty");
        }

        if (aantalInStock < 0 || minimumInStock < 0){
            throw new IllegalArgumentException("Value for 'aantalInStock' or 'minimumInStock' can not be less than zero");
        }

        if (verkoopPrijs == null || verkoopPrijs.doubleValue() < 0) {
            throw new IllegalArgumentException("Value for 'verkoopprijs' can not be null or less than zero");
        }

        if (actuelePrijs == null) {
            actuelePrijs = verkoopPrijs;
        }

        if ((aankoopPrijs != null && aankoopPrijs.doubleValue() < 0) || actuelePrijs.doubleValue() < 0) {
            throw new IllegalArgumentException("Value for 'aankoopPrijs' or 'actuelePrijs' can not be less than zero");
        }

        this.merk = merk;
        this.code = code;
        this.omschrijving = omschrijving;
        this.leverancierId = leverancierId;
        this.aantalInStock = aantalInStock;
        this.minimumInStock = minimumInStock;
        this.aankoopPrijs = aankoopPrijs;
        this.verkoopPrijs = verkoopPrijs;
        this.actuelePrijs = actuelePrijs;
    }

    public void setArtikelId(UUID artikelId) {
        this.artikelId = artikelId;
    }

}
