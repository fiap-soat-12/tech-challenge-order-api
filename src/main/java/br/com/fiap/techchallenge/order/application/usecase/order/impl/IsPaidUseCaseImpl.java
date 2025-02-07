package br.com.fiap.techchallenge.order.application.usecase.order.impl;

import br.com.fiap.techchallenge.order.application.exceptions.DoesNotExistException;
import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.application.usecase.order.IsPaidUseCase;

import java.util.UUID;

public class IsPaidUseCaseImpl implements IsPaidUseCase {

	private final OrderPersistence persistence;

	public IsPaidUseCaseImpl(OrderPersistence persistence) {
		this.persistence = persistence;
	}

	@Override
	public Boolean isOrderPaid(UUID id) {
		var orderFound = persistence.findById(id).orElseThrow(() -> new DoesNotExistException("Order does not exist!"));
		return orderFound.getIsPaid();
	}

}
