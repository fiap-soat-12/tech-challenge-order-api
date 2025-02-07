package br.com.fiap.techchallenge.order.application.usecase.order.impl;

import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.application.usecase.EvolveOrderUseCase;
import br.com.fiap.techchallenge.order.application.usecase.order.UpdateOrderStatusUseCase;
import br.com.fiap.techchallenge.order.application.usecase.order.impl.evolve.EvolveRules;
import br.com.fiap.techchallenge.order.domain.models.enums.OrderStatusEnum;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.OrderEvolveDTO;

import java.util.List;

public class EvolveOrderUseCaseImpl implements EvolveOrderUseCase {

	private final OrderPersistence persistence;
	private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

	private final List<EvolveRules> evolveRules;

	public EvolveOrderUseCaseImpl(OrderPersistence persistence, UpdateOrderStatusUseCase updateOrderStatusUseCase, List<EvolveRules> evolveRules) {
		this.persistence = persistence;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
        this.evolveRules = evolveRules;
	}

	@Override
	public void evolveOrder(OrderEvolveDTO orderEvolve) {
		var order = persistence.findById(orderEvolve.orderId()).orElseThrow();

		if(OrderStatusEnum.RECEIVED.equals(order.getStatus())){
			updateOrderStatusUseCase.evolveToPreparing(orderEvolve.orderId(), orderEvolve.isPaid());
			return;
		}

		evolveRules.forEach(rule -> rule.evolveTo(order));

		persistence.update(order);
	}

}
