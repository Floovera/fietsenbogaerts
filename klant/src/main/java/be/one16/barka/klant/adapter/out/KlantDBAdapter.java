package be.one16.barka.klant.adapter.out;

import be.one16.barka.klant.adapter.mapper.KlantJpaEntityMapper;
import be.one16.barka.klant.adapter.out.repository.KlantRepository;
import be.one16.barka.klant.domain.Klant;
import be.one16.barka.klant.domain.exceptions.EntityNotFoundException;
import be.one16.barka.klant.port.out.CreateKlantPort;
import be.one16.barka.klant.port.out.DeleteKlantPort;
import be.one16.barka.klant.port.out.LoadKlantenPort;
import be.one16.barka.klant.port.out.UpdateKlantPort;
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
public class KlantDBAdapter implements LoadKlantenPort, CreateKlantPort, UpdateKlantPort, DeleteKlantPort {

    private final KlantRepository klantRepository;

    private final KlantJpaEntityMapper klantJpaEntityMapper;

    public KlantDBAdapter(KlantRepository klantRepository, KlantJpaEntityMapper klantJpaEntityMapper) {
        this.klantRepository = klantRepository;
        this.klantJpaEntityMapper = klantJpaEntityMapper;
    }

    @Override
    public Klant retrieveKlantById(UUID id) {
        KlantJpaEntity klantJpaEntity = getKlantJpaEntityById(id);

        return klantJpaEntityMapper.mapJpaEntityToKlant(klantJpaEntity);
    }

    @Override
    public Page<Klant> retrieveKlantenByFilterAndSort(String naam, Pageable pageable) {
        Specification<KlantJpaEntity> specification = Specification.where(naam == null ? null : (Specification<KlantJpaEntity>) ((root, query, builder) -> builder.like(root.get("naam"), "%" + naam + "%")));
        return klantRepository.findAll(specification, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()))
                .map(klantJpaEntityMapper::mapJpaEntityToKlant);
    }

    @Override
    public void createKlant(Klant klant) {
        KlantJpaEntity klantJpaEntity = new KlantJpaEntity();

        klantJpaEntity.setUuid(klant.getKlantId());
        klantJpaEntity.setNaam(klant.getNaam());
        klantJpaEntity.setKlantType(klant.getKlantType());
        klantJpaEntity.setStraat(klant.getStraat());
        klantJpaEntity.setHuisnummer(klant.getHuisnummer());
        klantJpaEntity.setBus(klant.getBus());
        klantJpaEntity.setPostcode(klant.getPostcode());
        klantJpaEntity.setGemeente(klant.getGemeente());
        klantJpaEntity.setLand(klant.getLand());
        klantJpaEntity.setTelefoonnummer(klant.getTelefoonnummer());
        klantJpaEntity.setMobiel(klant.getMobiel());
        klantJpaEntity.setEmail(klant.getEmail());
        klantJpaEntity.setBtwNummer(klant.getBtwNummer());
        klantJpaEntity.setOpmerkingen(klant.getOpmerkingen());

        klantRepository.save(klantJpaEntity);
    }

    @Override
    public void updateKlant(Klant klant) {
        KlantJpaEntity klantJpaEntity = getKlantJpaEntityById(klant.getKlantId());

        klantJpaEntity.setNaam(klant.getNaam());
        klantJpaEntity.setKlantType(klant.getKlantType());
        klantJpaEntity.setStraat(klant.getStraat());
        klantJpaEntity.setHuisnummer(klant.getHuisnummer());
        klantJpaEntity.setBus(klant.getBus());
        klantJpaEntity.setPostcode(klant.getPostcode());
        klantJpaEntity.setGemeente(klant.getGemeente());
        klantJpaEntity.setLand(klant.getLand());
        klantJpaEntity.setTelefoonnummer(klant.getTelefoonnummer());
        klantJpaEntity.setMobiel(klant.getMobiel());
        klantJpaEntity.setEmail(klant.getEmail());
        klantJpaEntity.setBtwNummer(klant.getBtwNummer());
        klantJpaEntity.setOpmerkingen(klant.getOpmerkingen());

        klantRepository.save(klantJpaEntity);
    }

    @Override
    public void deleteKlant(UUID id) {
        KlantJpaEntity klantJpaEntity = getKlantJpaEntityById(id);
        klantRepository.delete(klantJpaEntity);
    }

    private KlantJpaEntity getKlantJpaEntityById(UUID id) {
        return klantRepository.findByUuid(id).orElseThrow(() -> new EntityNotFoundException(String.format("Klant with uuid %s doesn't exist", id)));
    }
}
