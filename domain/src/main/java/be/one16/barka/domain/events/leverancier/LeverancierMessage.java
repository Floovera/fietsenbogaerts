package be.one16.barka.domain.events.leverancier;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class LeverancierMessage {

    private UUID leverancierId;
    private String naam;
    private String straat;
    private String huisnummer;
    private String bus;
    private String postcode;
    private String gemeente;
    private String land;
    private String telefoonnummer;
    private String mobiel;
    private String fax;
    private String email;
    private String btwNummer;
    private String opmerkingen;
}
