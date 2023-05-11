package be.one16.barka.magazijn.adapters.out;

import be.one16.barka.domain.exceptions.EntityNotFoundException;
import be.one16.barka.domain.exceptions.LinkedEntityNotFoundException;
import be.one16.barka.leverancier.adapter.out.LeverancierJpaEntity;
import be.one16.barka.magazijn.adapters.mapper.ArtikelLeverancierJpaEntityMapper;
import be.one16.barka.magazijn.adapters.out.repository.ArtikelLeverancierRepository;
import be.one16.barka.magazijn.domain.Artikel;
import be.one16.barka.magazijn.domain.Leverancier;
import be.one16.barka.magazijn.ports.out.*;
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
public class ArtikelLeverancierDBAdapter implements ArtikelLeverancierCreatePort{

    private final ArtikelLeverancierRepository artikelLeverancierRepository;

    private final ArtikelLeverancierJpaEntityMapper artikelLeverancierJpaEntityMapper;

    public ArtikelLeverancierDBAdapter(ArtikelLeverancierRepository artikelLeverancierRepository, ArtikelLeverancierJpaEntityMapper artikelLeverancierJpaEntityMapper) {
        this.artikelLeverancierRepository = artikelLeverancierRepository;
        this.artikelLeverancierJpaEntityMapper = artikelLeverancierJpaEntityMapper;
    }

    public Leverancier retrieveArtikelLeverancierById(UUID id) {
        return artikelLeverancierJpaEntityMapper.mapJpaEntityToLeverancier(getArtikelLeverancierJpaEntityById(id));
    }

    private ArtikelLeverancierJpaEntity getArtikelLeverancierJpaEntityById(UUID id) {
        return artikelLeverancierRepository.findByUuid(id).orElseThrow(() -> new LinkedEntityNotFoundException(String.format("Leverancier with uuid %s doesn't exist", id)));
    }

    public void createArtikelLeverancier(Leverancier leverancier) {
        ArtikelLeverancierJpaEntity artikelLeverancierJpaEntity = new ArtikelLeverancierJpaEntity();
        artikelLeverancierJpaEntity.setUuid(leverancier.getLeverancierId());
        artikelLeverancierJpaEntity.setNaam(leverancier.getNaam());

        artikelLeverancierRepository.save(artikelLeverancierJpaEntity);
    }


   /* public void updateArtikel(Artikel artikel) {
        ArtikelJpaEntity artikelJpaEntity = getArtikelJpaEntityById(artikel.getArtikelId());

        fillJpaEntityWithArtikelData(artikelJpaEntity, artikel);

        artikelRepository.save(artikelJpaEntity);
    }


    public void deleteArtikel(UUID id) {
        ArtikelJpaEntity artikelJpaEntity = getArtikelJpaEntityById(id);
        artikelRepository.delete(artikelJpaEntity);
    }*/



}
