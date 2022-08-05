package be.one16.barka.magazijn.adapters.in;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ArtikelDto {

    private UUID artikelId;
    private String merk;
    private String code;
    private String omschrijving;
    private UUID leverancierId;
    private String leverancier;
    private int aantalInStock;
    private int minimumInStock;
    private BigDecimal aankoopPrijs;
    private BigDecimal verkoopPrijs;
    private BigDecimal actuelePrijs;

}
