package be.one16.barka.klant.adapter.out.repository;

import be.one16.barka.klant.adapter.out.verkoop.VerkoopJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerkoopRepository extends JpaRepository<VerkoopJpaEntity, Long>, JpaSpecificationExecutor<VerkoopJpaEntity> {

    Optional<VerkoopJpaEntity> findByUuid(UUID uuid);
    Optional <VerkoopJpaEntity> findTopByOrderByIdDesc();

}
