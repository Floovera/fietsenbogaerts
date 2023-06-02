package be.one16.barka.klant.core.order;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.domain.exceptions.EntityNotFoundException;
import be.one16.barka.klant.adapter.out.order.OrderJpaEntity;
import be.one16.barka.klant.adapter.out.repository.OrderRepository;
import be.one16.barka.klant.common.OrderType;
import be.one16.barka.klant.common.exceptions.KlantNotFoundException;
import be.one16.barka.klant.domain.Klant;
import be.one16.barka.klant.domain.Order;
import be.one16.barka.klant.ports.in.klant.KlantenQuery;
import be.one16.barka.klant.ports.in.order.OrderQuery;
import be.one16.barka.klant.ports.in.order.UpdateOrderCommand;
import be.one16.barka.klant.ports.in.order.UpdateOrderUnitOfWork;
import be.one16.barka.klant.ports.out.order.UpdateOrderPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@UnitOfWork
public class DefaultUpdateOrderUnitOfWork implements UpdateOrderUnitOfWork {

    private final List<UpdateOrderPort> updateOrderPorts;
    private final KlantenQuery klantenquery;
    private final OrderQuery orderQuery;
    private final OrderRepository orderRepository;

    private static final String REPARATIENUMMER_REGEX = "^(\\d){6}$";

    public DefaultUpdateOrderUnitOfWork(List<UpdateOrderPort> updateOrderPorts, KlantenQuery klantenquery, OrderQuery orderQuery, OrderRepository orderRepository) {
        this.updateOrderPorts = updateOrderPorts;
        this.klantenquery = klantenquery;
        this.orderQuery = orderQuery;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public void updateOrder(UpdateOrderCommand updateOrderCommand) throws KlantNotFoundException {

        UUID calculatedKlantId = null;
        Order orderRetrieved = orderQuery.retrieveOrderById(updateOrderCommand.orderId());
        String initalOrderNummer = orderRetrieved.getOrderNummer();
        OrderType initalOrderType = orderRetrieved.getOrderType();
        OrderType newOrderType = updateOrderCommand.orderType();

        if (newOrderType != initalOrderType) {
            boolean switchOK = orderRetrieved.checkSwitchType(newOrderType);
            if(!switchOK){
                throw new IllegalArgumentException("Only switches from 'Verkoop' to 'Reparatie' or 'Factuur' and switches from 'Reparatie' to 'Factuur' are allowed");
            }
        }

        //Regex reparatienummer wordt gecontroleerd voor een order van type factuur of reparatie
        if(!updateOrderCommand.orderType().equals(OrderType.VERKOOP) && !updateOrderCommand.reparatieNummer().matches(REPARATIENUMMER_REGEX)){
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
                .orderNummer(initalOrderNummer)
                .build();

        //OrderNummer wordt aangemaakt voor een order met type factuur
        if(newOrderType != initalOrderType){
            if(newOrderType == OrderType.FACTUUR){
                order.setOrderNummer(retrieveLastSequence(order.getDatum().getYear()));}
            }
        updateOrderPorts.forEach(port -> port.updateOrder(order));

    }

    private Klant getKlant(UUID klantID) {
        return klantenquery.retrieveKlantById(klantID);
    }

    private int retrieveLastSequence(int year){
        Optional<OrderJpaEntity> factuur = orderRepository.findTopByJaarOrderBySequenceDesc(year);
        int sequence = 0;
        if (!factuur.isEmpty()) {
            sequence = factuur.get().getSequence();
        }
        return sequence;
    }

}
