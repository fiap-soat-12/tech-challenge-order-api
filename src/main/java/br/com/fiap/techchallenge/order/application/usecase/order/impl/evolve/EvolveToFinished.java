package br.com.fiap.techchallenge.order.application.usecase.order.impl.evolve;

import br.com.fiap.techchallenge.order.domain.models.Order;
import br.com.fiap.techchallenge.order.domain.models.enums.OrderStatusEnum;

public class EvolveToFinished implements EvolveRules {

	@Override
	public void evolveTo(Order order) {
		if (OrderStatusEnum.READY.equals(order.getStatus())) {
			order.setStatusFinished();
		}
	}
}
