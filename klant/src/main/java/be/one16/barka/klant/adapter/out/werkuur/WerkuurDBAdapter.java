package be.one16.barka.klant.adapter.out.werkuur;

import be.one16.barka.domain.exceptions.EntityNotFoundException;
import be.one16.barka.klant.adapter.mapper.werkuur.WerkuurJpaEntityMapper;
import be.one16.barka.klant.adapter.out.repository.OrderRepository;
import be.one16.barka.klant.adapter.out.repository.WerkuurRepository;
import be.one16.barka.klant.domain.Werkuur;
import be.one16.barka.klant.ports.out.werkuur.LoadWerkurenPort;
import be.one16.barka.klant.ports.out.werkuur.WerkuurCreatePort;
import be.one16.barka.klant.ports.out.werkuur.WerkuurDeletePort;
import be.one16.barka.klant.ports.out.werkuur.WerkuurUpdatePort;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WerkuurDBAdapter implements LoadWerkurenPort, WerkuurCreatePort, WerkuurUpdatePort, WerkuurDeletePort {

    private final WerkuurRepository werkuurRepository;
    private final OrderRepository orderRepository;
    private final WerkuurJpaEntityMapper werkuurJpaEntityMapper;

    public WerkuurDBAdapter(WerkuurRepository werkuurRepository, OrderRepository orderRepository, WerkuurJpaEntityMapper werkuurJpaEntityMapper) {
        this.werkuurRepository = werkuurRepository;
        this.orderRepository = orderRepository;
        this.werkuurJpaEntityMapper = werkuurJpaEntityMapper;
    }


    private WerkuurJpaEntity getWerkuurJpaEntityById(UUID id) {
        return werkuurRepository.findByUuid(id).orElseThrow(() -> new EntityNotFoundException(String.format("Werkuur with uuid %s doesn't exist", id)));
    }

    @Override
    public Werkuur retrieveWerkuurOfOrder(UUID id, UUID orderId) {
        WerkuurJpaEntity werkuurJpaEntity = getWerkuurJpaEntityById(id);

        if (!werkuurJpaEntity.getOrderuuid().equals(orderId)) {
            throw new IllegalArgumentException(String.format("Werkuur with uuid %s doesn't belong to the order with uuid %s", id, orderId));
        }

        return werkuurJpaEntityMapper.mapJpaEntityToWerkuur(werkuurJpaEntity);
    }

    @Override
    public List<Werkuur> retrieveWerkurenOfOrder(UUID orderId) {
        return werkuurRepository.findAllByOrderuuid(orderId).stream().map(werkuurJpaEntityMapper::mapJpaEntityToWerkuur).toList();
    }

    private void fillJpaEntityWithWerkuurData(WerkuurJpaEntity werkuurJpaEntity, Werkuur werkuur) {
        werkuurJpaEntity.setDatum(werkuur.getDatum());
        werkuurJpaEntity.setAantalUren(werkuur.getAantalUren());
        werkuurJpaEntity.setUurTarief(werkuur.getUurTarief());
        werkuurJpaEntity.setBtwPerc(werkuur.getBtwPerc());
        werkuurJpaEntity.setTotaalExclusBtw(werkuur.getTotaalExclusBtw());
        werkuurJpaEntity.setTotaalInclusBtw(werkuur.getTotaalInclusBtw());
        werkuurJpaEntity.setBtwBedrag(werkuur.getBtwBedrag());
    }

    @Override
    public void createWerkuur(Werkuur werkuur) {
        WerkuurJpaEntity werkuurJpaEntity = new WerkuurJpaEntity();

        werkuurJpaEntity.setUuid(werkuur.getWerkuurId());
        werkuurJpaEntity.setOrderuuid(werkuur.getOrderId());
        fillJpaEntityWithWerkuurData(werkuurJpaEntity, werkuur);

        werkuurRepository.save(werkuurJpaEntity);
    }

    @Override
    public void updateWerkuur(Werkuur werkuur) {
        WerkuurJpaEntity werkuurJpaEntity = getWerkuurJpaEntityById(werkuur.getWerkuurId());

        if (!werkuurJpaEntity.getOrderuuid().equals(werkuur.getOrderId())) {
            throw new IllegalArgumentException(String.format("Werkuur with uuid %s doesn't belong to the order with uuid %s", werkuur.getWerkuurId(), werkuur.getOrderId()));
        }

        fillJpaEntityWithWerkuurData(werkuurJpaEntity,werkuur);
        werkuurRepository.save(werkuurJpaEntity);
    }

    @Override
    public void deleteWerkuur(UUID werkuurId, UUID orderId) {
        WerkuurJpaEntity werkuurJpaEntity = getWerkuurJpaEntityById(werkuurId);

        if (!werkuurJpaEntity.getOrderuuid().equals(orderId)) {
            throw new IllegalArgumentException(String.format("Werkuur with uuid %s doesn't belong to the order with uuid %s", werkuurJpaEntity.getUuid(), orderId));
        }

        werkuurRepository.delete(werkuurJpaEntity);
    }


}
