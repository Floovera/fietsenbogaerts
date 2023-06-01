package be.one16.barka.klant.adapter.out.order;

import be.one16.barka.domain.exceptions.EntityNotFoundException;
import be.one16.barka.klant.adapter.mapper.order.OrderJpaEntityMapper;
import be.one16.barka.klant.adapter.out.repository.OrderRepository;
import be.one16.barka.klant.common.OrderType;
import be.one16.barka.klant.domain.Order;
import be.one16.barka.klant.ports.out.order.CreateOrderPort;
import be.one16.barka.klant.ports.out.order.DeleteOrderPort;
import be.one16.barka.klant.ports.out.order.LoadOrdersPort;
import be.one16.barka.klant.ports.out.order.UpdateOrderPort;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@org.springframework.core.annotation.Order(Ordered.HIGHEST_PRECEDENCE)
public class OrderDBAdapter implements LoadOrdersPort, CreateOrderPort, UpdateOrderPort, DeleteOrderPort {

    private final OrderRepository orderRepository;

    private final OrderJpaEntityMapper orderJpaEntityMapper;

    public OrderDBAdapter(OrderRepository orderRepository, OrderJpaEntityMapper orderJpaEntityMapper) {
        this.orderRepository = orderRepository;
        this.orderJpaEntityMapper = orderJpaEntityMapper;
    }

    @Override
    public Order retrieveOrderById(UUID id) {
        OrderJpaEntity orderJpaEntity = getOrderJpaEntityById(id);

        return orderJpaEntityMapper.mapJpaEntityToOrder(orderJpaEntity);
    }

    @Override
    public Page<Order> retrieveOrdersByFilterAndSort(String naam, Pageable pageable) {
        Specification<OrderJpaEntity> specification = Specification.where(naam == null ? null : (Specification<OrderJpaEntity>) ((root, query, builder) -> builder.like(root.get("naam"), "%" + naam + "%")));
        return orderRepository.findAll(specification, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()))
                .map(orderJpaEntityMapper::mapJpaEntityToOrder);
    }

    @Override
    public void createOrder(Order order) {
        int year = order.getDatum().getYear();

        OrderJpaEntity orderJpaEntity = new OrderJpaEntity();

        orderJpaEntity.setUuid(order.getOrderId());
        orderJpaEntity.setOrderType(order.getOrderType());
        orderJpaEntity.setNaam(order.getNaam());
        orderJpaEntity.setOpmerkingen(order.getOpmerkingen());
        orderJpaEntity.setDatum(order.getDatum());
        orderJpaEntity.setJaar(year);
        orderJpaEntity.setKlantId(order.getKlantId());
        orderJpaEntity.setReparatieNummer(order.getReparatieNummer());
        orderJpaEntity.setOrderNummer(order.getOrderNummer());
        orderJpaEntity.setSequence(decideOnSequence(order));
        orderRepository.save(orderJpaEntity);
    }

    @Override
    public void updateOrder(Order order) {
        int year = order.getDatum().getYear();

        OrderJpaEntity orderJpaEntity = getOrderJpaEntityById(order.getOrderId());
        orderJpaEntity.setOrderType(order.getOrderType());
        orderJpaEntity.setNaam(order.getNaam());
        orderJpaEntity.setOpmerkingen(order.getOpmerkingen());
        orderJpaEntity.setDatum(order.getDatum());
        orderJpaEntity.setJaar(year);
        orderJpaEntity.setKlantId(order.getKlantId());
        orderJpaEntity.setReparatieNummer(order.getReparatieNummer());
        orderJpaEntity.setOrderNummer(order.getOrderNummer());
        orderJpaEntity.setSequence(decideOnSequence(order));
        orderRepository.save(orderJpaEntity);
    }

    @Override
    public void deleteOrder(UUID id) {
        OrderJpaEntity orderJpaEntity = getOrderJpaEntityById(id);
        orderRepository.delete(orderJpaEntity);
    }

    private OrderJpaEntity getOrderJpaEntityById(UUID id) {
        return orderRepository.findByUuid(id).orElseThrow(() -> new EntityNotFoundException(String.format("Order with uuid %s doesn't exist", id)));
    }

    private int decideOnSequence(Order order){
        int year = order.getDatum().getYear();
        int sequence = 0;
        OrderType orderType = order.getOrderType();
        //Enkel voor facturen, anders 0
        if(orderType==OrderType.FACTUUR){
            //Enkel wanneer er nog geen orderNummer bestaat, maken we een nieuwe sequence aan
            if(order.getOrderNummer()!=null) {
                Optional<OrderJpaEntity> factuur = orderRepository.findTopByJaarOrderBySequenceDesc(year);
                int sequenceLastFactuur = 0;
                if (!factuur.isEmpty()) {
                    sequenceLastFactuur = factuur.get().getSequence();
                    sequence = sequenceLastFactuur + 1;
                }else{
                    //Wanneer er geen factuur gevonden wordt, is dit de eerste factuur
                    sequence = 1;
                }
            }else{
                //Anders blijft de sequence dezelfde
                OrderJpaEntity orderJpaEntity = getOrderJpaEntityById(order.getOrderId());
                sequence = orderJpaEntity.getSequence();
            }
        }

        return sequence;
    }


}
