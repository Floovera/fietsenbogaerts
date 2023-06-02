package be.one16.barka.klant.ports.in.materiaal;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateMateriaalCommand(UUID materiaalId, UUID artikelId, String artikelMerk, String artikelCode, String artikelOmschrijving, int aantalArtikels, BigDecimal verkoopPrijsArtikel, int korting, int btwPerc, UUID orderId) {

    public UpdateMateriaalCommand {
        if (StringUtils.isEmpty(artikelMerk)) {
            throw new IllegalArgumentException("Value for 'artikelMerk' can not be null or empty");
        }

        if (StringUtils.isEmpty(artikelCode)) {
            throw new IllegalArgumentException("Value for 'artikelCode' can not be null or empty");
        }

        if (StringUtils.isEmpty(artikelOmschrijving)) {
            throw new IllegalArgumentException("Value for 'artikelOmschrijving' can not be null or empty");
        }

        if (aantalArtikels == 0) {
            throw new IllegalArgumentException("Value for 'aantalArtikels' can not be 0");
        }

        if (verkoopPrijsArtikel.compareTo(new BigDecimal("0.00")) == 0) {
            throw new IllegalArgumentException("Value for 'verkoopPrijsArtikel' can not be 0.0");
        }

        if (btwPerc != 6 && btwPerc != 21) {
            throw new IllegalArgumentException("Value for 'btw perc' should be 6 or 21");
        }

    }
}
