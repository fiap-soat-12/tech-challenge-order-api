package br.com.fiap.techchallenge.order.infra.config.bean;

import br.com.fiap.techchallenge.order.application.usecase.order.impl.evolve.EvolveToReady;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EvolveToReadyConfig {

	@Bean
	public EvolveToReady evolveToReady() {
		return new EvolveToReady();
	}

}
