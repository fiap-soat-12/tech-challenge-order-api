package br.com.fiap.techchallenge.order.infra.config.bean;

import br.com.fiap.techchallenge.order.application.persistence.CustomerPersistence;
import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.application.persistence.ProductPersistence;
import br.com.fiap.techchallenge.order.application.usecase.order.impl.CreateOrderUseCaseImpl;
import br.com.fiap.techchallenge.order.infra.gateway.producer.payment.PaymentProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateOrderUseCaseConfig {

	@Bean
	public CreateOrderUseCaseImpl createOrderUseCaseImpl(OrderPersistence persistence,
														 ProductPersistence productPersistence, CustomerPersistence customerPersistence,
														 PaymentProducer paymentProducer) {
		return new CreateOrderUseCaseImpl(persistence, productPersistence, customerPersistence, paymentProducer);
	}
}
