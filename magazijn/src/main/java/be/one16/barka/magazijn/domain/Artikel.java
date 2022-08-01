package be.one16.barka.magazijn.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class Artikel {

    private UUID artikelId;
    private String merk;
    private String code;
    private String omschrijving;
    private Integer aantalInStock;
    private Integer minimumInStock;
    private BigDecimal aankoopPrijs;
    private BigDecimal verkoopPrijs;
    private BigDecimal actuelePrijs;
}
