package be.one16.barka.leverancier.adapter.out;

import be.one16.barka.domain.exceptions.EntityNotFoundException;
import be.one16.barka.leverancier.adapter.mapper.LeverancierJpaEntityMapper;
import be.one16.barka.leverancier.adapter.out.repository.LeverancierRepository;
import be.one16.barka.leverancier.domain.Leverancier;
import be.one16.barka.leverancier.ports.out.leverancier.LeverancierCreatePort;
import be.one16.barka.leverancier.ports.out.leverancier.LeverancierDeletePort;
import be.one16.barka.leverancier.ports.out.leverancier.LeverancierUpdatePort;
import be.one16.barka.leverancier.ports.out.leverancier.LoadLeveranciersPort;
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
public class LeverancierDBAdapter implements LoadLeveranciersPort, LeverancierCreatePort, LeverancierUpdatePort, LeverancierDeletePort {

    private final LeverancierRepository leverancierRepository;
    private final LeverancierJpaEntityMapper leverancierJpaEntityMapper;

    public LeverancierDBAdapter(LeverancierRepository leverancierRepository, LeverancierJpaEntityMapper leverancierJpaEntityMapper) {
        this.leverancierRepository = leverancierRepository;
        this.leverancierJpaEntityMapper = leverancierJpaEntityMapper;
    }

    @Override
    public Leverancier retrieveLeverancierById(UUID id) {
        return leverancierJpaEntityMapper.mapJpaEntityToLeverancier(getLeverancierJpaEntityById(id));
    }

    @Override
    public Page<Leverancier> retrieveLeverancierByFilterAndSort(String naam, Pageable pageable) {
        Specification<LeverancierJpaEntity> specification = Specification.where(naam == null ? null : (Specification<LeverancierJpaEntity>) ((root, query, builder) -> builder.like(root.get("naam"), "%" + naam + "%")));

        return leverancierRepository.findAll(specification, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()))
                .map(leverancierJpaEntityMapper::mapJpaEntityToLeverancier);
    }

    @Override
    public void createLeverancier(Leverancier leverancier) {
        LeverancierJpaEntity leverancierJpaEntity = new LeverancierJpaEntity();

        leverancierJpaEntity.setUuid(leverancier.getLeverancierId());
        fillJpaEntityWithLeverancierData(leverancierJpaEntity, leverancier);

        leverancierRepository.save(leverancierJpaEntity);
    }

    @Override
    public void updateLeverancier(Leverancier leverancier) {
        LeverancierJpaEntity leverancierJpaEntity = getLeverancierJpaEntityById(leverancier.getLeverancierId());

        fillJpaEntityWithLeverancierData(leverancierJpaEntity, leverancier);

        leverancierRepository.save(leverancierJpaEntity);
    }

    @Override
    public void deleteLeverancier(UUID id) {
        LeverancierJpaEntity leverancierJpaEntity = getLeverancierJpaEntityById(id);
        leverancierRepository.delete(leverancierJpaEntity);
    }

    private LeverancierJpaEntity getLeverancierJpaEntityById(UUID id) {
        return leverancierRepository.findByUuid(id).orElseThrow(() -> new EntityNotFoundException(String.format("Leverancier with uuid %s doesn't exist", id)));
    }

    private void fillJpaEntityWithLeverancierData(LeverancierJpaEntity leverancierJpaEntity, Leverancier leverancier) {
        leverancierJpaEntity.setNaam(leverancier.getNaam());
        leverancierJpaEntity.setStraat(leverancier.getStraat());
        leverancierJpaEntity.setHuisnummer(leverancier.getHuisnummer());
        leverancierJpaEntity.setBus(leverancier.getBus());
        leverancierJpaEntity.setPostcode(leverancier.getPostcode());
        leverancierJpaEntity.setGemeente(leverancier.getGemeente());
        leverancierJpaEntity.setLand(leverancier.getLand());
        leverancierJpaEntity.setTelefoonnummer(leverancier.getTelefoonnummer());
        leverancierJpaEntity.setMobiel(leverancier.getMobiel());
        leverancierJpaEntity.setFax(leverancier.getFax());
        leverancierJpaEntity.setEmail(leverancier.getEmail());
        leverancierJpaEntity.setBtwNummer(leverancier.getBtwNummer());
        leverancierJpaEntity.setOpmerkingen(leverancier.getOpmerkingen());

    }

}
