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
import be.one16.barka.klant.ports.in.order.CreateOrderCommand;
import be.one16.barka.klant.ports.in.order.CreateOrderUnitOfWork;
import be.one16.barka.klant.ports.in.order.OrderQuery;
import be.one16.barka.klant.ports.out.order.CreateOrderPort;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@UnitOfWork
public class DefaultCreateOrderUnitOfWork implements CreateOrderUnitOfWork {

    private final List<CreateOrderPort> createOrderPorts;

    private final KlantenQuery klantenquery;

    private final OrderRepository orderRepository;
    private static final String REPARATIENUMMER_REGEX = "^(\\d){6}$";

    public DefaultCreateOrderUnitOfWork(List<CreateOrderPort> createOrderPorts, KlantenQuery klantenquery, OrderRepository orderRepository) {
        this.createOrderPorts = createOrderPorts;
        this.klantenquery = klantenquery;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public UUID createOrder(CreateOrderCommand createOrderCommand) throws KlantNotFoundException {

        UUID calculatedKlantId = null;

        //Regex reparatienummer wordt gecontroleerd voor een order van type factuur of reparatie
        if(!createOrderCommand.orderType().equals(OrderType.VERKOOPP) && !createOrderCommand.reparatieNummer().matches(REPARATIENUMMER_REGEX)){
                throw new IllegalArgumentException("Value for 'ReparatieNummer' must be 6 digits");
        }

        //KlantId wordt gecontroleerd
        if (createOrderCommand.klantId() != null) {
            try {
                Klant klantRetrieved = getKlant(createOrderCommand.klantId());
                calculatedKlantId = klantRetrieved.getKlantId();
            } catch (EntityNotFoundException e) {
                throw new KlantNotFoundException(String.format("Did not find the klant for the provided UUID: %s", createOrderCommand.klantId()));
            }
        }

        Order order = Order.builder()
                .orderId(UUID.randomUUID())
                .orderType(createOrderCommand.orderType())
                .naam(createOrderCommand.naam())
                .opmerkingen(createOrderCommand.opmerkingen())
                .datum(createOrderCommand.datum())
                .klantId(calculatedKlantId)
                .reparatieNummer(createOrderCommand.reparatieNummer())
                .build();


        //OrderNummer wordt aangemaakt voor een order met type factuur
        if(createOrderCommand.orderType().equals(OrderType.FACTUUR)){
            order.setOrderNummer(retrieveLastSequence(order.getDatum().getYear()));}

        createOrderPorts.forEach(port -> port.createOrder(order));

        return order.getOrderId();
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
