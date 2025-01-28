package br.com.fiap.techchallenge.order.application.usecase.order;

import java.util.UUID;

public interface IsPaidUseCase {

	Boolean isOrderPaid(UUID id);

}
