package br.com.fiap.techchallenge.order.application.usecase;

import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.OrderEvolveDTO;

public interface EvolveOrderUseCase {
	void evolveOrder(OrderEvolveDTO orderEvolve);
}
