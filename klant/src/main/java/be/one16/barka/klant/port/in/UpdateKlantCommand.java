package be.one16.barka.klant.port.in;

import be.one16.barka.klant.common.KlantType;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public record UpdateKlantCommand (UUID klantId, String naam, KlantType klantType, String straat, String huisnummer, String bus, String postcode, String gemeente, String land, String telefoonnummer, String mobiel, String email, String btwNummer, String opmerkingen) {

    public UpdateKlantCommand {
        if (StringUtils.isEmpty(naam)) {
            throw new IllegalArgumentException("Value for 'naam' can not be null or empty");
        }
    }
}
