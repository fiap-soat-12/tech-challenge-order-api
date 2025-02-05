package br.com.fiap.techchallenge.order.application.usecase.order;

import java.util.UUID;

public interface UpdateOrderStatusUseCase {
	void evolveToPreparing(UUID orderId, Boolean isPaid);
}
