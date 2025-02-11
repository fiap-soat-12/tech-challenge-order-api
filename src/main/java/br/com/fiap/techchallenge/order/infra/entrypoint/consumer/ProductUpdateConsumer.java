package br.com.fiap.techchallenge.order.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.order.application.usecase.product.UpdateProductUseCase;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.ProductUpdateDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.mapper.ProductMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class ProductUpdateConsumer {
    private final UpdateProductUseCase updateProductUseCase;
    private final ProductMapper mapper;
    private final ObjectMapper objectMapper;

    public ProductUpdateConsumer(UpdateProductUseCase updateProductUseCase, ProductMapper mapper, ObjectMapper objectMapper) {
        this.updateProductUseCase = updateProductUseCase;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @SqsListener("${sqs.queue.product.update.listener}")
    public void receiveMessage(String message) throws JsonProcessingException {
        updateProductUseCase.update(mapper.toProduct(
                objectMapper.readValue(message, ProductUpdateDTO.class)));
    }
}