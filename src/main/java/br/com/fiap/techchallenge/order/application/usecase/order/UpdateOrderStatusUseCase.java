package br.com.fiap.techchallenge.order.application.usecase.order;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.UUID;

public interface UpdateOrderStatusUseCase {
	void evolveToPreparing(UUID orderId, Boolean isPaid) throws JsonProcessingException;
}
