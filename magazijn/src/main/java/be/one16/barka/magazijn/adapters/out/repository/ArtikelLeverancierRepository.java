package be.one16.barka.magazijn.adapters.out.repository;

import be.one16.barka.magazijn.adapters.out.ArtikelLeverancierJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArtikelLeverancierRepository extends JpaRepository<ArtikelLeverancierJpaEntity, Long> {

    Optional<ArtikelLeverancierJpaEntity> findByUuid(UUID uuid);

}
