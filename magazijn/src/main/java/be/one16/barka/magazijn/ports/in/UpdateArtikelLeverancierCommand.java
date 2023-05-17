package be.one16.barka.magazijn.ports.in;

import be.one16.barka.magazijn.common.StatusLeverancier;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public record UpdateArtikelLeverancierCommand(UUID uuid, String naam, StatusLeverancier status)  {
    public UpdateArtikelLeverancierCommand {
        if (StringUtils.isEmpty(naam)){
            throw new IllegalArgumentException("Value for 'naam' can not be null or empty");
        }
    }

}
