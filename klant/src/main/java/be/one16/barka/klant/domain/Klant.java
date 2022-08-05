package be.one16.barka.klant.domain;

import be.one16.barka.klant.common.KlantType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class Klant {

    private UUID klantId;
    private String naam;
    private KlantType klantType;
    private String straat;
    private String huisnummer;
    private String bus;
    private String postcode;
    private String gemeente;
    private String land;
    private String telefoonnummer;
    private String mobiel;
    private String email;
    private String btwNummer;
    private String opmerkingen;

}
