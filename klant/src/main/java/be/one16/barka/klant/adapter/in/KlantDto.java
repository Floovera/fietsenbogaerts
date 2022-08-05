package be.one16.barka.klant.adapter.in;

import be.one16.barka.klant.common.KlantType;
import lombok.Data;

import java.util.UUID;

@Data
public class KlantDto {

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
