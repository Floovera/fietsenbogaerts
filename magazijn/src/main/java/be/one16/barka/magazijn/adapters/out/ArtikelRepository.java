package be.one16.barka.magazijn.adapters.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ArtikelRepository extends JpaRepository<ArtikelJpaEntity, BigInteger> {

}
