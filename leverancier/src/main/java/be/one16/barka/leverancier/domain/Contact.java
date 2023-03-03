package be.one16.barka.leverancier.domain;

import be.one16.barka.leverancier.common.ContactMethode;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Contact {

    private UUID contactId;
    private String naam;
    private String onderwerp;
    private ContactMethode contactMethode;
    private String gegevens;
    private UUID leverancierId;

}
