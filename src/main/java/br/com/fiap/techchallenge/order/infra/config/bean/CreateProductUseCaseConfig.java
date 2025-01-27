package br.com.fiap.techchallenge.order.infra.config.bean;

import br.com.fiap.techchallenge.order.application.persistence.ProductPersistence;
import br.com.fiap.techchallenge.order.application.usecase.product.impl.CreateProductUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateProductUseCaseConfig {

	@Bean
	public CreateProductUseCaseImpl createProductUseCaseImpl(ProductPersistence persistence) {
		return new CreateProductUseCaseImpl(persistence);
	}

}