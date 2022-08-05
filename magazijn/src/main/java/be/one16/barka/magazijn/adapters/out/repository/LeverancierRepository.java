package be.one16.barka.magazijn.adapters.out.repository;

import be.one16.barka.magazijn.adapters.out.LeverancierJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LeverancierRepository extends JpaRepository<LeverancierJpaEntity, Long> {

    Optional<LeverancierJpaEntity> findByUuid(UUID uuid);

}
