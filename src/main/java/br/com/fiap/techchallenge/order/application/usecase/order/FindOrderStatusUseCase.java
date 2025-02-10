package br.com.fiap.techchallenge.order.application.usecase.order;

import br.com.fiap.techchallenge.order.domain.models.Order;

import java.util.UUID;

public interface FindOrderStatusUseCase {

    Order findOrderStatus(UUID id);
}
