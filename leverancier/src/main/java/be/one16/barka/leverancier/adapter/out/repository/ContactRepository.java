package be.one16.barka.leverancier.adapter.out.repository;

import be.one16.barka.leverancier.adapter.out.ContactJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<ContactJpaEntity, Long>, JpaSpecificationExecutor<ContactJpaEntity> {

    Optional<ContactJpaEntity> findByUuid(UUID uuid);

    List<ContactJpaEntity> findAllByLeverancierUuid(UUID leverancierUuid);

}
