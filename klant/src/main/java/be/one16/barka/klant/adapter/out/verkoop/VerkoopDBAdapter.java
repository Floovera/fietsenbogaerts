package be.one16.barka.klant.adapter.out.verkoop;

import be.one16.barka.domain.exceptions.EntityNotFoundException;
import be.one16.barka.klant.adapter.mapper.verkoop.VerkoopJpaEntityMapper;
import be.one16.barka.klant.adapter.out.repository.VerkoopRepository;
import be.one16.barka.klant.domain.Verkoop;
import be.one16.barka.klant.ports.out.verkoop.CreateVerkoopPort;
import be.one16.barka.klant.ports.out.verkoop.DeleteVerkoopPort;
import be.one16.barka.klant.ports.out.verkoop.LoadVerkopenPort;
import be.one16.barka.klant.ports.out.verkoop.UpdateVerkoopPort;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class VerkoopDBAdapter implements LoadVerkopenPort, CreateVerkoopPort, UpdateVerkoopPort, DeleteVerkoopPort {

    private final VerkoopRepository verkoopRepository;

    private final VerkoopJpaEntityMapper verkoopJpaEntityMapper;

    public VerkoopDBAdapter(VerkoopRepository verkoopRepository, VerkoopJpaEntityMapper verkoopJpaEntityMapper) {
        this.verkoopRepository = verkoopRepository;
        this.verkoopJpaEntityMapper = verkoopJpaEntityMapper;
    }

    @Override
    public Verkoop retrieveVerkoopById(UUID id) {
        VerkoopJpaEntity verkoopJpaEntity = getVerkoopJpaEntityById(id);

        return verkoopJpaEntityMapper.mapJpaEntityToVerkoop(verkoopJpaEntity);
    }

    @Override
    public Page<Verkoop> retrieveVerkopenByFilterAndSort(String naam, Pageable pageable) {
        Specification<VerkoopJpaEntity> specification = Specification.where(naam == null ? null : (Specification<VerkoopJpaEntity>) ((root, query, builder) -> builder.like(root.get("naam"), "%" + naam + "%")));
        return verkoopRepository.findAll(specification, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()))
                .map(verkoopJpaEntityMapper::mapJpaEntityToVerkoop);
    }

    @Override
    public void createVerkoop(Verkoop verkoop) {
        VerkoopJpaEntity verkoopJpaEntity = new VerkoopJpaEntity();

        verkoopJpaEntity.setUuid(verkoop.getVerkoopId());
        verkoopJpaEntity.setOrderType(verkoop.getOrderType());
        verkoopJpaEntity.setNaam(verkoop.getNaam());
        verkoopJpaEntity.setOpmerkingen(verkoop.getOpmerkingen());
        verkoopJpaEntity.setDatum(verkoop.getDatum());
        verkoopJpaEntity.setKlantId(verkoop.getKlantId());
        verkoopJpaEntity.setReparatieNummer(verkoop.getReparatieNummer());
        verkoopJpaEntity.setOrderNummer(verkoop.getOrderNummer());
        verkoopRepository.save(verkoopJpaEntity);
    }

    @Override
    public void updateVerkoop(Verkoop verkoop) {
        VerkoopJpaEntity verkoopJpaEntity = getVerkoopJpaEntityById(verkoop.getVerkoopId());
        verkoopJpaEntity.setOrderType(verkoop.getOrderType());
        verkoopJpaEntity.setNaam(verkoop.getNaam());
        verkoopJpaEntity.setOpmerkingen(verkoop.getOpmerkingen());
        verkoopJpaEntity.setDatum(verkoop.getDatum());
        verkoopJpaEntity.setKlantId(verkoop.getKlantId());
        verkoopJpaEntity.setReparatieNummer(verkoop.getReparatieNummer());
        verkoopJpaEntity.setOrderNummer(verkoop.getOrderNummer());

        verkoopRepository.save(verkoopJpaEntity);
    }

    @Override
    public void deleteVerkoop(UUID id) {
        VerkoopJpaEntity verkoopJpaEntity = getVerkoopJpaEntityById(id);
        verkoopRepository.delete(verkoopJpaEntity);
    }

    private VerkoopJpaEntity getVerkoopJpaEntityById(UUID id) {
        return verkoopRepository.findByUuid(id).orElseThrow(() -> new EntityNotFoundException(String.format("Verkoop with uuid %s doesn't exist", id)));
    }


}
