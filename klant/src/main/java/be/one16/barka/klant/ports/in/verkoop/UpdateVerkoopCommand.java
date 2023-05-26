package be.one16.barka.klant.ports.in.verkoop;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public record UpdateVerkoopCommand(UUID verkoopId, String naam, String opmerkingen,UUID klantId) {

    public UpdateVerkoopCommand {
        if (StringUtils.isEmpty(naam)) {
            throw new IllegalArgumentException("Value for 'naam' can not be null or empty");
        }
    }
}
