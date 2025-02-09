package br.com.fiap.techchallenge.order.application.usecase;

import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.OrderEvolveDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface EvolveOrderUseCase {
	void evolveOrder(OrderEvolveDTO orderEvolve) throws JsonProcessingException;
}
