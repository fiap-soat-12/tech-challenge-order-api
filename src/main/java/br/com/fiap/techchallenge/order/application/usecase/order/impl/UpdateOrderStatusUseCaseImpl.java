package br.com.fiap.techchallenge.order.application.usecase.order.impl;

import br.com.fiap.techchallenge.order.application.exceptions.DoesNotExistException;
import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.application.usecase.order.UpdateOrderStatusUseCase;
import br.com.fiap.techchallenge.order.domain.models.Order;
import br.com.fiap.techchallenge.order.domain.models.OrderDetails;
import br.com.fiap.techchallenge.order.domain.models.OrderTimestamps;
import br.com.fiap.techchallenge.order.domain.models.enums.OrderStatusEnum;
import br.com.fiap.techchallenge.order.infra.gateway.producer.cook.CookProducer;
import br.com.fiap.techchallenge.order.infra.gateway.producer.cook.dto.CookDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Transactional
public class UpdateOrderStatusUseCaseImpl implements UpdateOrderStatusUseCase {

	private final OrderPersistence persistence;
	private final CookProducer cookProducer;

	public UpdateOrderStatusUseCaseImpl(OrderPersistence persistence, CookProducer cookProducer) {
		this.persistence = persistence;
        this.cookProducer = cookProducer;
    }

	@Override
	public void evolveToPreparing(UUID orderId, Boolean isPaid) throws JsonProcessingException {
		var found = persistence.findById(orderId)
			.orElseThrow(() -> new DoesNotExistException("Order does no exist!"));

		if (Boolean.TRUE.equals(isPaid)) {
			this.sendToCook(found, isPaid);
		} else {
			found.cancelOrder(isPaid);
		}

		persistence.update(found);
	}

	private void sendToCook(Order order, Boolean isPaid) throws JsonProcessingException {
		order.prepareOrder(isPaid);
		cookProducer.sendToCook(new CookDTO(order));
	}

	@Scheduled(fixedRate = 600000)
	@Transactional
	public void updateOrderStatus() {
		LocalDateTime thirtyMinutesAgo = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"))
			.minusMinutes(30)
			.toLocalDateTime();
		List<Order> ordersFound = persistence.findByStatusAndCreatedAtBefore(OrderStatusEnum.RECEIVED,
				thirtyMinutesAgo);

		for (Order order : ordersFound) {
			order.setStatusFinished();

			var details = new OrderDetails(order.getSequence(), order.getStatus(),
					order.getIsPaid(), order.getProducts(), order.getCustomer(), order.getPaymentId());

			var updatedOrder = new Order(order.getId(), order.getAmount(), details,
					new OrderTimestamps(order.getCreatedAt(), order.getUpdatedAt()));

			persistence.update(updatedOrder);
		}
	}
}
