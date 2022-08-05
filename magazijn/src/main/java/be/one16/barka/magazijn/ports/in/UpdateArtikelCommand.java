package be.one16.barka.magazijn.ports.in;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateArtikelCommand (UUID artikelId, String merk, String code, String omschrijving, UUID leverancierId, int aantalInStock, int minimumInStock, BigDecimal aankoopPrijs, BigDecimal verkoopPrijs, BigDecimal actuelePrijs) {

    public UpdateArtikelCommand {
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
    }
}
