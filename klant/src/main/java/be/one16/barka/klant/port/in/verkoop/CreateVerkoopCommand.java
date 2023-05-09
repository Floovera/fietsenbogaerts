package be.one16.barka.klant.port.in.verkoop;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public record CreateVerkoopCommand(String naam,
                                   String opmerkingen,
                                   UUID klantId) {

    public CreateVerkoopCommand {
        if (StringUtils.isEmpty(naam)) {
            throw new IllegalArgumentException("Value for 'naam' can not be null or empty");
        }
    }
}
