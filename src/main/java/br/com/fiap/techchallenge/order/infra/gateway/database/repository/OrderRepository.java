package br.com.fiap.techchallenge.order.infra.gateway.database.repository;


import br.com.fiap.techchallenge.order.domain.models.enums.OrderStatusEnum;
import br.com.fiap.techchallenge.order.infra.gateway.database.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

	List<OrderEntity> findByStatusNot(OrderStatusEnum status);

	Optional<OrderEntity> findByPaymentId(String paymentId);

	List<OrderEntity> findByStatusAndCreatedAtBefore(OrderStatusEnum status, LocalDateTime createdAt);

}
