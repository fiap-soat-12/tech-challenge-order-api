package br.com.fiap.techchallenge.order.infra.config.bean;

import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.application.usecase.order.UpdateOrderStatusUseCase;
import br.com.fiap.techchallenge.order.application.usecase.order.impl.EvolveOrderUseCaseImpl;
import br.com.fiap.techchallenge.order.application.usecase.order.impl.evolve.EvolveToFinished;
import br.com.fiap.techchallenge.order.application.usecase.order.impl.evolve.EvolveToReady;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class EvolveOrderUseCaseConfig {

	@Bean
	public EvolveOrderUseCaseImpl evolveOrderUseCase(OrderPersistence orderPersistence,
													 EvolveToFinished evolveToFinished, UpdateOrderStatusUseCase updateOrderStatusUseCase, EvolveToReady evolveToReady) {
		return new EvolveOrderUseCaseImpl(orderPersistence, updateOrderStatusUseCase, List.of(evolveToFinished, evolveToReady));
	}
}
