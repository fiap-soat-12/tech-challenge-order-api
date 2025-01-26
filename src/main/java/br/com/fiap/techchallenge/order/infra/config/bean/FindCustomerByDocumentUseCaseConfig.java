package br.com.fiap.techchallenge.order.infra.config.bean;

import br.com.fiap.techchallenge.order.application.persistence.CustomerPersistence;
import br.com.fiap.techchallenge.order.application.usecase.customer.impl.FindCustomerByDocumentUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindCustomerByDocumentUseCaseConfig {

	@Bean
	public FindCustomerByDocumentUseCaseImpl findCustomerByDocumentUseCaseImpl(CustomerPersistence persistence) {
		return new FindCustomerByDocumentUseCaseImpl(persistence);
	}

}
