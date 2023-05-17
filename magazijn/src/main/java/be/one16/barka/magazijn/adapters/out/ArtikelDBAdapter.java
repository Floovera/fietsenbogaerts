package be.one16.barka.magazijn.adapters.out;

import be.one16.barka.domain.exceptions.EntityNotFoundException;
import be.one16.barka.domain.exceptions.LinkedEntityNotFoundException;
import be.one16.barka.magazijn.adapters.mapper.ArtikelJpaEntityMapper;
import be.one16.barka.magazijn.adapters.out.repository.ArtikelLeverancierRepository;
import be.one16.barka.magazijn.adapters.out.repository.ArtikelRepository;
import be.one16.barka.magazijn.domain.Artikel;
import be.one16.barka.magazijn.ports.out.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ArtikelDBAdapter implements LoadArtikelsPort, ArtikelCreatePort, ArtikelUpdatePort, ArtikelDeletePort, ArtikelUniqueCodePort {

    private final ArtikelRepository artikelRepository;
    private final ArtikelLeverancierRepository artikelLeverancierRepository;

    private final ArtikelJpaEntityMapper  artikelJpaEntityMapper;

    public ArtikelDBAdapter(ArtikelRepository artikelRepository, ArtikelLeverancierRepository artikelLeverancierRepository, ArtikelJpaEntityMapper artikelJpaEntityMapper) {
        this.artikelRepository = artikelRepository;
        this.artikelLeverancierRepository = artikelLeverancierRepository;
        this.artikelJpaEntityMapper = artikelJpaEntityMapper;
    }

    @Override
    public Artikel retrieveArtikelById(UUID id) {
        return artikelJpaEntityMapper.mapJpaEntityToArtikel(getArtikelJpaEntityById(id));
    }

    @Override
    public Page<Artikel> retrieveArtikelByFilterAndSort(String code, String merk, String omschrijving, UUID leverancierId, Pageable pageable) {
        Specification<ArtikelJpaEntity> specification = Specification.where(code == null ? null : (Specification<ArtikelJpaEntity>) ((root, query, builder) -> builder.like(root.get("code"), "%" + code + "%")))
                .and(merk == null ? null : (root, query, builder) -> builder.like(root.get("merk"), "%" + merk + "%"))
                .and(omschrijving == null ? null : (root, query, builder) -> builder.like(root.get("omschrijving"), "%" + omschrijving + "%"))
                .and(leverancierId == null ? null : (root, query, builder) -> builder.equal(root.get("leverancier").get("uuid"), leverancierId));

        return artikelRepository.findAll(specification, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()))
                .map(artikelJpaEntityMapper::mapJpaEntityToArtikel);
    }

    @Override
    public boolean leverancierHasArticles(UUID leverancierId) {
        List<ArtikelJpaEntity> artikels = artikelRepository.findAllByLeverancierId(leverancierId);
        return !artikels.isEmpty();
    }

    @Override
    public void createArtikel(Artikel artikel) {
        ArtikelJpaEntity artikelJpaEntity = new ArtikelJpaEntity();

        artikelJpaEntity.setUuid(artikel.getArtikelId());
        fillJpaEntityWithArtikelData(artikelJpaEntity, artikel);

        artikelRepository.save(artikelJpaEntity);
    }

    @Override
    public void updateArtikel(Artikel artikel) {
        ArtikelJpaEntity artikelJpaEntity = getArtikelJpaEntityById(artikel.getArtikelId());

        fillJpaEntityWithArtikelData(artikelJpaEntity, artikel);

        artikelRepository.save(artikelJpaEntity);
    }

    @Override
    public void deleteArtikel(UUID id) {
        ArtikelJpaEntity artikelJpaEntity = getArtikelJpaEntityById(id);
        artikelRepository.delete(artikelJpaEntity);
    }

    @Override
    public boolean checkUniqueArtikelCode(String code) {
        return !artikelRepository.existsByCode(code);
    }

    private ArtikelJpaEntity getArtikelJpaEntityById(UUID id) {
        return artikelRepository.findByUuid(id).orElseThrow(() -> new EntityNotFoundException(String.format("Artikel with uuid %s doesn't exist", id)));
    }

    private ArtikelLeverancierJpaEntity getLeverancierJpaEntityById(UUID id) {
        return artikelLeverancierRepository.findByUuid(id).orElseThrow(() -> new LinkedEntityNotFoundException(String.format("Leverancier with uuid %s doesn't exist", id)));
    }

    private void fillJpaEntityWithArtikelData(ArtikelJpaEntity artikelJpaEntity, Artikel artikel) {
        artikelJpaEntity.setMerk(artikel.getMerk());
        artikelJpaEntity.setCode(artikel.getCode());
        artikelJpaEntity.setOmschrijving(artikel.getOmschrijving());
        artikelJpaEntity.setAantalInStock(artikel.getAantalInStock());
        artikelJpaEntity.setMinimumInStock(artikel.getMinimumInStock());
        artikelJpaEntity.setAankoopPrijs(artikel.getAankoopPrijs());
        artikelJpaEntity.setVerkoopPrijs(artikel.getVerkoopPrijs());
        artikelJpaEntity.setActuelePrijs(artikel.getActuelePrijs());

        ArtikelLeverancierJpaEntity artikelLeverancierJpaEntity = getLeverancierJpaEntityById(artikel.getLeverancier().getLeverancierId());
        artikelJpaEntity.setLeverancier(artikelLeverancierJpaEntity);
    }

}
