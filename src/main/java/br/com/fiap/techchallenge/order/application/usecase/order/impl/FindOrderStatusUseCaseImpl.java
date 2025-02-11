package br.com.fiap.techchallenge.order.application.usecase.order.impl;

import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.application.usecase.order.FindOrderStatusUseCase;
import br.com.fiap.techchallenge.order.domain.models.Order;

import java.util.UUID;

public class FindOrderStatusUseCaseImpl implements FindOrderStatusUseCase {

    private final OrderPersistence persistence;

    public FindOrderStatusUseCaseImpl(OrderPersistence persistence) {
        this.persistence = persistence;
    }

    @Override
    public Order findOrderStatus(UUID id) {
        return persistence.findById(id).orElseThrow();
    }
}
