package be.one16.barka.klant.adapter.out.repository;

import be.one16.barka.klant.adapter.out.order.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderJpaEntity, Long>, JpaSpecificationExecutor<OrderJpaEntity> {

    Optional<OrderJpaEntity> findByUuid(UUID uuid);
    Optional <OrderJpaEntity> findTopByOrderByIdDesc();

    Optional <OrderJpaEntity> findTopByOrderBySequenceDesc();

}
