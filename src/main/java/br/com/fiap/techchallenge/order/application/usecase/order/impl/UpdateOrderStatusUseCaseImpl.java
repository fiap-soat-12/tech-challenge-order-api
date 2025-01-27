package br.com.fiap.techchallenge.order.application.usecase.order.impl;

import br.com.fiap.techchallenge.order.application.exceptions.DoesNotExistException;
import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.application.usecase.order.UpdateOrderStatusUseCase;
import br.com.fiap.techchallenge.order.domain.models.Order;
import br.com.fiap.techchallenge.order.domain.models.enums.OrderStatusEnum;
import br.com.fiap.techchallenge.order.infra.gateway.producer.cook.CookProducer;
import br.com.fiap.techchallenge.order.infra.gateway.producer.cook.dto.CookDTO;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Transactional
public class UpdateOrderStatusUseCaseImpl implements UpdateOrderStatusUseCase {

	private final OrderPersistence persistence;
	private final CookProducer cookProducer;

	public UpdateOrderStatusUseCaseImpl(OrderPersistence persistence, CookProducer cookProducer) {
		this.persistence = persistence;
        this.cookProducer = cookProducer;
    }

	@Override
	public void updateStatusByPaymentDataId(String paymentDataId, String status) {
		var orderFound = persistence.findByPaymentId(paymentDataId)
			.orElseThrow(() -> new DoesNotExistException("Order does no exist!"));

		if ("approved".equals(status)) {
			orderFound.setStatus(OrderStatusEnum.PREPARING);
			cookProducer.sendToCook(new CookDTO());
		}

		var isPaid = OrderStatusEnum.PREPARING.equals(orderFound.getStatus()) || orderFound.isPaid();

		var updatedOrder = new Order(orderFound, isPaid);

		persistence.update(updatedOrder);
	}

	@Scheduled(fixedRate = 600000)
	@Transactional
	@Override
	public void updateOrderStatus() {
		LocalDateTime thirtyMinutesAgo = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"))
			.minusMinutes(30)
			.toLocalDateTime();
		List<Order> ordersFound = persistence.findByStatusAndCreatedAtBefore(OrderStatusEnum.RECEIVED,
				thirtyMinutesAgo);

		for (Order order : ordersFound) {
			order.setStatus(OrderStatusEnum.FINISHED);

			var updatedOrder = new Order(order.getId(), order.getAmount(), order.getSequence(), order.getStatus(),
					order.isPaid(), order.getProducts(), order.getCustomer(), order.getPaymentId(), order.getQr(),
					order.getCreatedAt(), order.getUpdatedAt());

			persistence.update(updatedOrder);
		}
	}

}
