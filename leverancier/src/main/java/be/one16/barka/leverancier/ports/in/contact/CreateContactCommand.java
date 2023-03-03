package be.one16.barka.leverancier.ports.in.contact;

import be.one16.barka.leverancier.common.ContactMethode;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public record CreateContactCommand(String naam, String onderwerp, ContactMethode contactMethode, String gegevens, UUID leverancierId) {

    public CreateContactCommand {
        if (StringUtils.isEmpty(naam)) {
            throw new IllegalArgumentException("Value for 'naam' can not be null or empty");
        }

        if (contactMethode == null) {
            throw new IllegalArgumentException("Value for 'contactMethode' can not be null");
        }

        if (StringUtils.isEmpty(gegevens)) {
            throw new IllegalArgumentException("Value for 'gegevens' can not be null or empty");
        }
    }
}
