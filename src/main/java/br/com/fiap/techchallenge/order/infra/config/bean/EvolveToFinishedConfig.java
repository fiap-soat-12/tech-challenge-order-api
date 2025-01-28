package br.com.fiap.techchallenge.order.infra.config.bean;

import br.com.fiap.techchallenge.order.application.usecase.order.impl.evolve.EvolveToFinished;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EvolveToFinishedConfig {

	@Bean
	public EvolveToFinished evolveToFinished() {
		return new EvolveToFinished();
	}

}
