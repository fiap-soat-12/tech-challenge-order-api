package br.com.fiap.techchallenge.order.application.usecase.order.impl.evolve;

import br.com.fiap.techchallenge.order.domain.models.Order;

public interface EvolveRules {
	void evolveTo(Order order);
}
