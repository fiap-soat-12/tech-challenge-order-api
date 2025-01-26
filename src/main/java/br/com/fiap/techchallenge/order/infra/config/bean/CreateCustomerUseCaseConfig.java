package br.com.fiap.techchallenge.order.infra.config.bean;

import br.com.fiap.techchallenge.order.application.persistence.CustomerPersistence;
import br.com.fiap.techchallenge.order.application.usecase.customer.impl.CreateCustomerUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateCustomerUseCaseConfig {

	@Bean
	public CreateCustomerUseCaseImpl createCustomerUseCaseImpl(CustomerPersistence persistence) {
		return new CreateCustomerUseCaseImpl(persistence);
	}

}
