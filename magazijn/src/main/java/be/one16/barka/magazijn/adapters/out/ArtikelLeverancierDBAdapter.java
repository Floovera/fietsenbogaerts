package be.one16.barka.magazijn.adapters.out;

import be.one16.barka.domain.exceptions.LinkedEntityNotFoundException;
import be.one16.barka.magazijn.adapters.mapper.ArtikelLeverancierJpaEntityMapper;
import be.one16.barka.magazijn.adapters.out.repository.ArtikelLeverancierRepository;
import be.one16.barka.magazijn.domain.Leverancier;
import be.one16.barka.magazijn.ports.out.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@Log4j2
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ArtikelLeverancierDBAdapter implements ArtikelLeverancierCreatePort, ArtikelLeverancierUpdatePort, ArtikelLeverancierDeletePort, LoadArtikelLeveranciersPort{

    private final ArtikelLeverancierRepository artikelLeverancierRepository;

    private final ArtikelLeverancierJpaEntityMapper artikelLeverancierJpaEntityMapper;

    public ArtikelLeverancierDBAdapter(ArtikelLeverancierRepository artikelLeverancierRepository, ArtikelLeverancierJpaEntityMapper artikelLeverancierJpaEntityMapper) {
        this.artikelLeverancierRepository = artikelLeverancierRepository;
        this.artikelLeverancierJpaEntityMapper = artikelLeverancierJpaEntityMapper;
    }

    @Override
    public Optional<Leverancier> retrieveArtikelLeverancierById(UUID id) {
        return Optional.ofNullable(artikelLeverancierJpaEntityMapper.mapJpaEntityToLeverancier(getArtikelLeverancierJpaEntityById(id)));
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


    public void updateArtikelLeverancier(Leverancier leverancier) {
        log.info("UUID die we  in updateartikelleverancier - " + leverancier.getLeverancierId());
        ArtikelLeverancierJpaEntity artikelLeverancierJpaEntity = getArtikelLeverancierJpaEntityById(leverancier.getLeverancierId());
        artikelLeverancierJpaEntity.setNaam(leverancier.getNaam());

        artikelLeverancierRepository.save(artikelLeverancierJpaEntity);
    }


    public void deleteArtikelLeverancier(UUID leverancierId) {
        ArtikelLeverancierJpaEntity artikelLeverancierJpaEntity = getArtikelLeverancierJpaEntityById(leverancierId);
        artikelLeverancierRepository.delete(artikelLeverancierJpaEntity);

    }



}
