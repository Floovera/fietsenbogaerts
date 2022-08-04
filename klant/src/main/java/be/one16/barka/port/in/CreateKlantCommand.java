package be.one16.barka.port.in;

import be.one16.barka.common.KlantType;
import org.apache.commons.lang3.StringUtils;

public record CreateKlantCommand(String naam,
                                 KlantType klantType,
                                 String straat,
                                 String huisnummer,
                                 String bus,
                                 String postcode,
                                 String gemeente,
                                 String land,
                                 String telefoonnummer,
                                 String mobiel,
                                 String email,
                                 String btwNummer,
                                 String opmerkingen) {

    public CreateKlantCommand {
        if (StringUtils.isEmpty(naam)) {
            throw new IllegalArgumentException("Value for 'naam' can not be null or empty");
        }
    }
}
