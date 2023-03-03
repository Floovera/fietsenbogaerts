package be.one16.barka.leverancier.ports.in.leverancier;

import org.apache.commons.lang3.StringUtils;

public record CreateLeverancierCommand(String naam, String straat, String huisnummer, String bus, String postcode, String gemeente, String land, String telefoonnummer, String mobiel, String fax, String email, String btwNummer, String opmerkingen) {

    public CreateLeverancierCommand {
        if (StringUtils.isEmpty(naam)) {
            throw new IllegalArgumentException("Value for 'naam' can not be null or empty");
        }
    }

}
