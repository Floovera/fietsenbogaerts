package be.one16.barka.port.in;

import be.one16.barka.common.KlantType;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@Getter
public final class UpdateKlantCommand {

    private UUID klantId;

    private final String naam;
    private final KlantType klantType;
    private final String straat;
    private final String huisnummer;
    private final String bus;
    private final String postcode;
    private final String gemeente;
    private final String land;
    private final String telefoonnummer;
    private final String mobiel;
    private final String email;
    private final String btwNummer;
    private final String opmerkingen;

    public UpdateKlantCommand(String naam, KlantType klantType, String straat, String huisnummer, String bus, String postcode, String gemeente, String land, String telefoonnummer, String mobiel, String email, String btwNummer, String opmerkingen) {
        if (StringUtils.isEmpty(naam)) {
            throw new IllegalArgumentException("Value for 'naam' can not be null or empty");
        }

        this.naam = naam;
        this.klantType = klantType;
        this.straat = straat;
        this.huisnummer = huisnummer;
        this.bus = bus;
        this.postcode = postcode;
        this.gemeente = gemeente;
        this.land = land;
        this.telefoonnummer = telefoonnummer;
        this.mobiel = mobiel;
        this.email = email;
        this.btwNummer = btwNummer;
        this.opmerkingen = opmerkingen;
    }

    public void setKlantId(UUID klantId) {
        this.klantId = klantId;
    }
}
