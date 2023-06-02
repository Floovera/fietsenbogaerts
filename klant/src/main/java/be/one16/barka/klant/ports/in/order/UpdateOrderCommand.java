package be.one16.barka.klant.ports.in.order;

import be.one16.barka.klant.common.OrderType;
import org.apache.commons.lang3.StringUtils;
import java.time.LocalDate;
import java.util.UUID;

public record UpdateOrderCommand(UUID orderId, OrderType orderType, String naam, String opmerkingen, LocalDate datum, UUID klantId, String reparatieNummer) {

    public UpdateOrderCommand {

        if(orderType == null){
            throw new IllegalArgumentException("Value for 'orderType' can not be null");
        }

        if(datum == null){
            throw new IllegalArgumentException("Value for 'datum' can not be null");
        }

        if (StringUtils.isEmpty(naam)) {
            throw new IllegalArgumentException("Value for 'naam' can not be null or empty");
        }

        if (orderType != OrderType.VERKOOP && reparatieNummer == null) {
            throw new IllegalArgumentException("Value for 'reparatieNummer' can not be null");
        }

        if (orderType == OrderType.FACTUUR && klantId == null) {
            throw new IllegalArgumentException("Value for 'klantId' can not be null");
        }
    }
}
