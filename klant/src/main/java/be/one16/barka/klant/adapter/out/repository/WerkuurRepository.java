package be.one16.barka.klant.adapter.out.repository;

import be.one16.barka.klant.adapter.out.werkuur.WerkuurJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WerkuurRepository extends JpaRepository<WerkuurJpaEntity, Long>, JpaSpecificationExecutor<WerkuurJpaEntity> {

    Optional<WerkuurJpaEntity> findByUuid(UUID uuid);

    List<WerkuurJpaEntity> findAllByOrderuuid(UUID orderUuid);

}
