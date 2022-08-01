package be.one16.barka.magazijn.ports.in;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

public record CreateArtikelCommand(String merk, String code, String omschrijving, int aantalInStock, int minimumInStock, BigDecimal aankoopPrijs, BigDecimal verkoopPrijs,BigDecimal actuelePrijs)  {

    public CreateArtikelCommand{
        if (StringUtils.isEmpty(merk) || StringUtils.isEmpty(code) || StringUtils.isEmpty(omschrijving)){
            throw new IllegalArgumentException();
        }

        if (aantalInStock < 0 || minimumInStock < 0){
            throw new IllegalArgumentException();
        }
        Objects.requireNonNull(aankoopPrijs);
        Objects.requireNonNull(verkoopPrijs);
        Objects.requireNonNull(actuelePrijs);
    }

}
