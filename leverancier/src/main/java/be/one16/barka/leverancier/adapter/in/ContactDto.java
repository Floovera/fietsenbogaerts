package be.one16.barka.leverancier.adapter.in;

import be.one16.barka.leverancier.common.ContactMethode;
import lombok.Data;

import java.util.UUID;

@Data
public class ContactDto {

    private UUID contactId;
    private String naam;
    private String onderwerp;
    private ContactMethode contactMethode;
    private String gegevens;

}
