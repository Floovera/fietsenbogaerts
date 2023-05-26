package be.one16.barka.klant.port.in.verkoop;

import be.one16.barka.klant.common.OrderType;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.UUID;

public record CreateVerkoopCommand(OrderType orderType, String naam, String opmerkingen, LocalDate datum, UUID klantId) {

    public CreateVerkoopCommand {

        if(orderType == null){
            throw new IllegalArgumentException("Value for 'orderType' can not be null");
        }

        if(datum == null){
            throw new IllegalArgumentException("Value for 'datum' can not be null");
        }

        if (StringUtils.isEmpty(naam)) {
            throw new IllegalArgumentException("Value for 'naam' can not be null or empty");
        }
    }
}
