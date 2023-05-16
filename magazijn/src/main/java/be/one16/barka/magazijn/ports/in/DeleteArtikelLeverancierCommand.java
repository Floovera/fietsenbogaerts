package be.one16.barka.magazijn.ports.in;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public record DeleteArtikelLeverancierCommand(UUID uuid, String naam)  {
    public DeleteArtikelLeverancierCommand {
        if (StringUtils.isEmpty(naam)){
            throw new IllegalArgumentException("Value for 'naam' can not be null or empty");
        }
    }

}
