package br.com.fiap.techchallenge.order.infra.config.bean;

import br.com.fiap.techchallenge.order.application.persistence.ProductPersistence;
import br.com.fiap.techchallenge.order.application.usecase.product.impl.UpdateProductUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateProductUseCaseConfig {

	@Bean
	public UpdateProductUseCaseImpl updateProductUseCaseImpl(ProductPersistence persistence) {
		return new UpdateProductUseCaseImpl(persistence);
	}
}