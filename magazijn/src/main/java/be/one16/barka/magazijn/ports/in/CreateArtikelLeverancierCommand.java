package be.one16.barka.magazijn.ports.in;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateArtikelLeverancierCommand(UUID uuid, String naam)  {
    public CreateArtikelLeverancierCommand {
        if (StringUtils.isEmpty(naam)){
            throw new IllegalArgumentException("Value for 'merk', 'code' or 'omschrijving' can not be null or empty");
        }
    }

}
