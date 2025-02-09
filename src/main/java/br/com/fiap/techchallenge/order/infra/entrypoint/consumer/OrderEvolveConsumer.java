package br.com.fiap.techchallenge.order.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.order.application.usecase.EvolveOrderUseCase;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.OrderEvolveDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEvolveConsumer {

    private final EvolveOrderUseCase evolveOrderUseCase;
    private final ObjectMapper objectMapper;

    public OrderEvolveConsumer(EvolveOrderUseCase evolveOrderUseCase, ObjectMapper objectMapper) {
        this.evolveOrderUseCase = evolveOrderUseCase;
        this.objectMapper = objectMapper;
    }

    @SqsListener("${sqs.queue.order.evolve.listener}")
    public void receiveMessage(String message) throws JsonProcessingException {
        evolveOrderUseCase.evolveOrder(objectMapper.readValue(message, OrderEvolveDTO.class));
    }
}
