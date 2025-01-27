package br.com.fiap.techchallenge.order.infra.config.bean;

import br.com.fiap.techchallenge.order.application.persistence.ProductPersistence;
import br.com.fiap.techchallenge.order.application.usecase.product.impl.DeleteProductByIdUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeleteProductByIdUseCaseConfig {

	@Bean
	public DeleteProductByIdUseCaseImpl deleteProductByIdUseCaseImpl(ProductPersistence persistence) {
		return new DeleteProductByIdUseCaseImpl(persistence);
	}
}