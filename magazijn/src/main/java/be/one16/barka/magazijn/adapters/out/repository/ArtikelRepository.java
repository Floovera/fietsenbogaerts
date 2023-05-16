package be.one16.barka.magazijn.adapters.out.repository;

import be.one16.barka.magazijn.adapters.out.ArtikelJpaEntity;
import be.one16.barka.magazijn.domain.Artikel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArtikelRepository extends JpaRepository<ArtikelJpaEntity, Long>, JpaSpecificationExecutor<ArtikelJpaEntity> {

    Optional<ArtikelJpaEntity> findByUuid(UUID uuid);

    @Query("SELECT count(*) > 0 FROM ArtikelJpaEntity a WHERE a.code = :code")
    boolean existsByCode(@Param("code") String code);

    @Query("SELECT a FROM ArtikelJpaEntity a JOIN ArtikelLeverancierJpaEntity b on a.leverancier.id = b.id WHERE b.uuid = :leverancierId")
    List<ArtikelJpaEntity> findAllByLeverancier_Id(UUID leverancierId);

}
