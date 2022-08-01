package be.one16.barka.magazijn.adapters.in;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ArtikelDto {
    private String merk;
    private String code;
    private String omschrijving;
    private int aantalInStock;
    private int minimumInStock;
    private BigDecimal aankoopPrijs;
    private BigDecimal verkoopPrijs;
    private BigDecimal actuelePrijs;
}
