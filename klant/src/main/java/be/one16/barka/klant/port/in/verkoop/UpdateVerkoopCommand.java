package be.one16.barka.klant.port.in.verkoop;

import be.one16.barka.klant.common.KlantType;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateVerkoopCommand(UUID verkoopId, String naam, String opmerkingen, LocalDate datum, UUID klantId) {

    public UpdateVerkoopCommand {

        if(datum == null){
            throw new IllegalArgumentException("Value for 'datum' can not be null");
        }

        if (StringUtils.isEmpty(naam)) {
            throw new IllegalArgumentException("Value for 'naam' can not be null or empty");
        }
    }
}
