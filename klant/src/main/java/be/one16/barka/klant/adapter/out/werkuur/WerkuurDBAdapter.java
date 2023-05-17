package be.one16.barka.klant.adapter.out.werkuur;

import be.one16.barka.domain.exceptions.EntityNotFoundException;
import be.one16.barka.klant.adapter.mapper.werkuur.WerkuurJpaEntityMapper;
import be.one16.barka.klant.adapter.out.repository.VerkoopRepository;
import be.one16.barka.klant.adapter.out.repository.WerkuurRepository;
import be.one16.barka.klant.domain.Werkuur;
import be.one16.barka.klant.ports.out.werkuur.LoadWerkurenPort;
import be.one16.barka.klant.ports.out.werkuur.WerkuurCreatePort;
import be.one16.barka.klant.ports.out.werkuur.WerkuurDeletePort;
import be.one16.barka.klant.ports.out.werkuur.WerkuurUpdatePort;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WerkuurDBAdapter implements LoadWerkurenPort, WerkuurCreatePort, WerkuurUpdatePort, WerkuurDeletePort {

    private final WerkuurRepository werkuurRepository;
    private final VerkoopRepository verkoopRepository;
    private final WerkuurJpaEntityMapper werkuurJpaEntityMapper;

    public WerkuurDBAdapter(WerkuurRepository werkuurRepository, VerkoopRepository verkoopRepository, WerkuurJpaEntityMapper werkuurJpaEntityMapper) {
        this.werkuurRepository = werkuurRepository;
        this.verkoopRepository = verkoopRepository;
        this.werkuurJpaEntityMapper = werkuurJpaEntityMapper;
    }


    private WerkuurJpaEntity getWerkuurJpaEntityById(UUID id) {
        return werkuurRepository.findByUuid(id).orElseThrow(() -> new EntityNotFoundException(String.format("Werkuur with uuid %s doesn't exist", id)));
    }

    @Override
    public Werkuur retrieveWerkuurOfVerkoop(UUID id, UUID verkoopId) {
        WerkuurJpaEntity werkuurJpaEntity = getWerkuurJpaEntityById(id);

        if (!werkuurJpaEntity.getVerkoopuuid().equals(verkoopId)) {
            throw new IllegalArgumentException(String.format("Werkuur with uuid %s doesn't belong to the verkoop with uuid %s", id, verkoopId));
        }

        return werkuurJpaEntityMapper.mapJpaEntityToWerkuur(werkuurJpaEntity);
    }

    @Override
    public List<Werkuur> retrieveWerkurenOfVerkoop(UUID verkoopId) {
        return werkuurRepository.findAllByVerkoopuuid(verkoopId).stream().map(werkuurJpaEntityMapper::mapJpaEntityToWerkuur).toList();
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
        werkuurJpaEntity.setVerkoopuuid(werkuur.getVerkoopId());
        fillJpaEntityWithWerkuurData(werkuurJpaEntity, werkuur);

        werkuurRepository.save(werkuurJpaEntity);
    }

    @Override
    public void updateWerkuur(Werkuur werkuur) {
        WerkuurJpaEntity werkuurJpaEntity = getWerkuurJpaEntityById(werkuur.getWerkuurId());

        if (!werkuurJpaEntity.getVerkoopuuid().equals(werkuur.getVerkoopId())) {
            throw new IllegalArgumentException(String.format("Werkuur with uuid %s doesn't belong to the Verkoop with uuid %s", werkuur.getWerkuurId(), werkuur.getVerkoopId()));
        }

        fillJpaEntityWithWerkuurData(werkuurJpaEntity,werkuur);
        werkuurRepository.save(werkuurJpaEntity);
    }

    @Override
    public void deleteWerkuur(UUID werkuurId, UUID verkoopId) {
        WerkuurJpaEntity werkuurJpaEntity = getWerkuurJpaEntityById(werkuurId);

        if (!werkuurJpaEntity.getVerkoopuuid().equals(verkoopId)) {
            throw new IllegalArgumentException(String.format("Werkuur with uuid %s doesn't belong to the Verkoop with uuid %s", werkuurJpaEntity.getUuid(), verkoopId));
        }

        werkuurRepository.delete(werkuurJpaEntity);
    }


}
