package br.com.fiap.techchallenge.order.infra.config.bean;


import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.application.usecase.order.impl.UpdateOrderStatusUseCaseImpl;
import br.com.fiap.techchallenge.order.infra.gateway.producer.cook.CookProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateOrderStatusUseCaseConfig {

	@Bean
	public UpdateOrderStatusUseCaseImpl updateOrderStatusUseCaseImpl(OrderPersistence orderPersistence,
																	 CookProducer cookProducer) {
		return new UpdateOrderStatusUseCaseImpl(orderPersistence, cookProducer);
	}

}
