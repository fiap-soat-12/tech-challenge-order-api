package br.com.fiap.techchallenge.order.infra.config.bean;

import br.com.fiap.techchallenge.order.application.persistence.ProductPersistence;
import br.com.fiap.techchallenge.order.application.usecase.product.impl.FindProductsByCategoryUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindProductsByCategoryUseCaseConfig {

	@Bean
	public FindProductsByCategoryUseCaseImpl findProductsByCategoryUseCaseImpl(ProductPersistence persistence) {
		return new FindProductsByCategoryUseCaseImpl(persistence);
	}
}