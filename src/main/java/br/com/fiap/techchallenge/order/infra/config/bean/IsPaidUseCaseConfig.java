package br.com.fiap.techchallenge.order.infra.config.bean;

import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.application.usecase.order.impl.IsPaidUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IsPaidUseCaseConfig {

	@Bean
	public IsPaidUseCaseImpl isPaidUseCaseImpl(OrderPersistence orderPersistence) {
		return new IsPaidUseCaseImpl(orderPersistence);
	}

}
