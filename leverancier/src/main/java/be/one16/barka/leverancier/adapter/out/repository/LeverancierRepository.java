package be.one16.barka.leverancier.adapter.out.repository;

import be.one16.barka.leverancier.adapter.out.LeverancierJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LeverancierRepository extends JpaRepository<LeverancierJpaEntity, Long>, JpaSpecificationExecutor<LeverancierJpaEntity> {

    Optional<LeverancierJpaEntity> findByUuid(UUID uuid);

}
