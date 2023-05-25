package be.one16.barka.klant.ports.in.materiaal;
import org.apache.commons.lang3.StringUtils;
import java.util.UUID;

public record CreateMateriaalCommand(String artikelMerk, String artikelCode, String artikelOmschrijving, int aantalArtikels, double verkoopPrijsArtikel, int korting, int btwPerc, UUID verkoopId) {

    public CreateMateriaalCommand {
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

        if (verkoopPrijsArtikel == 0.0) {
            throw new IllegalArgumentException("Value for 'verkoopPrijsArtikel' can not be 0.0");
        }

        if (btwPerc != 6 && btwPerc != 21) {
            throw new IllegalArgumentException("Value for 'btw perc' should be 6 or 21");
        }

    }
}