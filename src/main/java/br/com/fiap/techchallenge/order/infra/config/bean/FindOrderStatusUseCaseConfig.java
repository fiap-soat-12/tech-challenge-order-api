package br.com.fiap.techchallenge.order.infra.config.bean;

import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.application.usecase.order.impl.FindOrderStatusUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindOrderStatusUseCaseConfig {

	@Bean
	public FindOrderStatusUseCaseImpl findOrderStatusUseCaseImpl(OrderPersistence persistence) {
		return new FindOrderStatusUseCaseImpl(persistence);
	}
}