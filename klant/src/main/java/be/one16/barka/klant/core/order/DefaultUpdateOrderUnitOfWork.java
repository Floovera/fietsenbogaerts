package be.one16.barka.klant.core.order;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.domain.exceptions.EntityNotFoundException;
import be.one16.barka.klant.common.OrderType;
import be.one16.barka.klant.common.exceptions.KlantNotFoundException;
import be.one16.barka.klant.domain.Klant;
import be.one16.barka.klant.domain.Order;
import be.one16.barka.klant.ports.in.klant.KlantenQuery;
import be.one16.barka.klant.ports.in.order.UpdateOrderCommand;
import be.one16.barka.klant.ports.in.order.UpdateOrderUnitOfWork;
import be.one16.barka.klant.ports.out.order.UpdateOrderPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@UnitOfWork
public class DefaultUpdateOrderUnitOfWork implements UpdateOrderUnitOfWork {

    private final List<UpdateOrderPort> updateOrderPorts;
    private final KlantenQuery klantenquery;

    private static final String REPARATIENUMMER_REGEX = "^(\\d){6}$";

    public DefaultUpdateOrderUnitOfWork(List<UpdateOrderPort> updateOrderPorts, KlantenQuery klantenquery) {
        this.updateOrderPorts = updateOrderPorts;
        this.klantenquery = klantenquery;
    }

    @Override
    @Transactional
    public void updateOrder(UpdateOrderCommand updateOrderCommand) throws KlantNotFoundException {

        UUID calculatedKlantId = null;

        //Regex reparatienummer wordt gecontroleerd voor een order van type factuur of reparatie
        if(!updateOrderCommand.orderType().equals(OrderType.VERKOOPP) && !updateOrderCommand.reparatieNummer().matches(REPARATIENUMMER_REGEX)){
            throw new IllegalArgumentException("Value for 'ReparatieNummer' must be 6 digits");
        }

        if (updateOrderCommand.klantId() != null) {
            try {
                Klant klantRetrieved = getKlant(updateOrderCommand.klantId());
                calculatedKlantId = klantRetrieved.getKlantId();
            } catch (EntityNotFoundException e) {
                throw new KlantNotFoundException(String.format("Did not find the klant for the provided UUID: %s", updateOrderCommand.klantId()));
            }
        }

        Order order = Order.builder()
                .orderId(updateOrderCommand.orderId())
                .orderType(updateOrderCommand.orderType())
                .naam(updateOrderCommand.naam())
                .opmerkingen(updateOrderCommand.opmerkingen())
                .datum(updateOrderCommand.datum())
                .klantId(calculatedKlantId)
                .reparatieNummer(updateOrderCommand.reparatieNummer())
                .orderNummer(updateOrderCommand.orderNummer())
                .build();

        updateOrderPorts.forEach(port -> port.updateOrder(order));

    }

    private Klant getKlant(UUID klantID) {
        return klantenquery.retrieveKlantById(klantID);
    }
}
