package be.one16.barka.magazijn.adapters.out;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface ArtikelRepository extends JpaRepository<ArtikelJpaEntity, BigInteger> {

}
