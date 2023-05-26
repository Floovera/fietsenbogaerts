package be.one16.barka.klant.adapter.out.materiaal;

import be.one16.barka.domain.exceptions.EntityNotFoundException;
import be.one16.barka.klant.adapter.mapper.materiaal.MateriaalJpaEntityMapper;
import be.one16.barka.klant.adapter.out.repository.MateriaalRepository;
import be.one16.barka.klant.adapter.out.repository.VerkoopRepository;
import be.one16.barka.klant.domain.Materiaal;
import be.one16.barka.klant.ports.out.materiaal.LoadMaterialenPort;
import be.one16.barka.klant.ports.out.materiaal.MateriaalCreatePort;
import be.one16.barka.klant.ports.out.materiaal.MateriaalDeletePort;
import be.one16.barka.klant.ports.out.materiaal.MateriaalUpdatePort;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MateriaalDBAdapter implements LoadMaterialenPort, MateriaalCreatePort, MateriaalUpdatePort, MateriaalDeletePort {

    private final MateriaalRepository materiaalRepository;

    private final MateriaalJpaEntityMapper materiaalJpaEntityMapper;

    public MateriaalDBAdapter(MateriaalRepository materiaalRepository, MateriaalJpaEntityMapper materiaalJpaEntityMapper) {

        this.materiaalRepository = materiaalRepository;
        this.materiaalJpaEntityMapper = materiaalJpaEntityMapper;
    }

    private MateriaalJpaEntity getMateriaalJpaEntityById(UUID id) {
        return materiaalRepository.findByUuid(id).orElseThrow(() -> new EntityNotFoundException(String.format("Materiaal with uuid %s doesn't exist", id)));
    }

    @Override
    public Materiaal retrieveMateriaalOfVerkoop(UUID id, UUID verkoopId) {
        MateriaalJpaEntity materiaalJpaEntity = getMateriaalJpaEntityById(id);

        if (!materiaalJpaEntity.getVerkoopuuid().equals(verkoopId)) {
            throw new IllegalArgumentException(String.format("Materiaal with uuid %s doesn't belong to the Verkoop with uuid %s", id, verkoopId));
        }

        return materiaalJpaEntityMapper.mapJpaEntityToMateriaal(materiaalJpaEntity);
    }

    @Override
    public List<Materiaal> retrieveMaterialenOfVerkoop(UUID verkoopId) {
        return materiaalRepository.findAllByVerkoopuuid(verkoopId).stream().map(materiaalJpaEntityMapper::mapJpaEntityToMateriaal).toList();
    }

    private void fillJpaEntityWithMateriaalData(MateriaalJpaEntity materiaalJpaEntity, Materiaal materiaal) {
        materiaalJpaEntity.setArtikeluuid(materiaal.getArtikelId());
        materiaalJpaEntity.setArtikelMerk(materiaal.getArtikelMerk());
        materiaalJpaEntity.setArtikelCode(materiaal.getArtikelCode());
        materiaalJpaEntity.setArtikelOmschrijving(materiaal.getArtikelOmschrijving());
        materiaalJpaEntity.setAantalArtikels(materiaal.getAantalArtikels());
        materiaalJpaEntity.setVerkoopPrijsArtikel(materiaal.getVerkoopPrijsArtikel());
        materiaalJpaEntity.setKorting(materiaal.getKorting());
        materiaalJpaEntity.setBtwPerc(materiaal.getBtwPerc());
        materiaalJpaEntity.setTotaalExclusBtw(materiaal.getTotaalExclusBtw());
        materiaalJpaEntity.setTotaalInclusBtw(materiaal.getTotaalInclusBtw());
        materiaalJpaEntity.setBtwBedrag(materiaal.getBtwBedrag());

    }

    @Override
    public void createMateriaal(Materiaal materiaal) {
        MateriaalJpaEntity materiaalJpaEntity = new MateriaalJpaEntity();

        materiaalJpaEntity.setUuid(materiaal.getMateriaalId());
        materiaalJpaEntity.setVerkoopuuid(materiaal.getVerkoopId());
        fillJpaEntityWithMateriaalData(materiaalJpaEntity, materiaal);

        materiaalRepository.save(materiaalJpaEntity);
    }

    @Override
    public void updateMateriaal(Materiaal materiaal) {
        MateriaalJpaEntity materiaalJpaEntity = getMateriaalJpaEntityById(materiaal.getMateriaalId());

        if (!materiaalJpaEntity.getVerkoopuuid().equals(materiaal.getVerkoopId())) {
            throw new IllegalArgumentException(String.format("Materiaal with uuid %s doesn't belong to the Verkoop with uuid %s", materiaal.getMateriaalId(), materiaal.getVerkoopId()));
        }

        fillJpaEntityWithMateriaalData(materiaalJpaEntity, materiaal);

        materiaalRepository.save(materiaalJpaEntity);
    }

    @Override
    public void deleteMateriaal(UUID materiaalId, UUID verkoopId) {
        MateriaalJpaEntity materiaalJpaEntity = getMateriaalJpaEntityById(materiaalId);

        if (!materiaalJpaEntity.getVerkoopuuid().equals(verkoopId)) {
            throw new IllegalArgumentException(String.format("Materiaal with uuid %s doesn't belong to the Verkoop with uuid %s", materiaalJpaEntity.getUuid(), verkoopId));
        }

        materiaalRepository.delete(materiaalJpaEntity);
    }
}
