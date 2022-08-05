package be.one16.barka.klant.adapter.out.repository;

import be.one16.barka.klant.adapter.out.KlantJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface KlantRepository extends JpaRepository<KlantJpaEntity, Long>, JpaSpecificationExecutor<KlantJpaEntity> {

    Optional<KlantJpaEntity> findByUuid(UUID uuid);

}
