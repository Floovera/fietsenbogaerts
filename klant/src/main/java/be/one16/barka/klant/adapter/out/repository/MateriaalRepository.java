package be.one16.barka.klant.adapter.out.repository;
import be.one16.barka.klant.adapter.out.materiaal.MateriaalJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MateriaalRepository extends JpaRepository<MateriaalJpaEntity, Long>, JpaSpecificationExecutor<MateriaalJpaEntity> {

    Optional<MateriaalJpaEntity> findByUuid(UUID uuid);

    List<MateriaalJpaEntity> findAllByVerkoopuuid(UUID verkoopUuid);

}
