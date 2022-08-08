package be.one16.barka.leverancier.ports.in;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public record UpdateLeverancierCommand(UUID leverancierId, String naam, String straat, String huisnummer, String bus, String postcode, String gemeente, String land, String telefoonnummer, String mobiel, String fax, String email, String btwNummer, String opmerkingen) {

    public UpdateLeverancierCommand{
        if (StringUtils.isEmpty(naam)) {
            throw new IllegalArgumentException("Value for 'naam' can not be null or empty");
        }
    }

}
