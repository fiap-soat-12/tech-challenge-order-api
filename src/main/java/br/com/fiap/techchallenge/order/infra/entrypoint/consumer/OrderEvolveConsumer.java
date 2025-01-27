package br.com.fiap.techchallenge.order.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.order.application.usecase.EvolveOrderUseCase;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.OrderEvolveDTO;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEvolveConsumer {

    private final EvolveOrderUseCase evolveOrderUseCase;

    public OrderEvolveConsumer(EvolveOrderUseCase evolveOrderUseCase) {
        this.evolveOrderUseCase = evolveOrderUseCase;
    }

    @SqsListener("${sqs.queue.order.evolve.listener}")
    public void receiveMessage(OrderEvolveDTO dto){
        evolveOrderUseCase.evolveOrder(dto.orderId());
    }
}
