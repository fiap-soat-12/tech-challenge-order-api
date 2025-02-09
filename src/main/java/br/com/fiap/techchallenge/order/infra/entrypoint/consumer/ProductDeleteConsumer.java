package br.com.fiap.techchallenge.order.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.order.application.usecase.product.DeleteProductByIdUseCase;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.ProductDeleteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class ProductDeleteConsumer {

    private final DeleteProductByIdUseCase deleteProductByIdUseCase;
    private final ObjectMapper objectMapper;

    public ProductDeleteConsumer(DeleteProductByIdUseCase deleteProductByIdUseCase, ObjectMapper objectMapper) {
        this.deleteProductByIdUseCase = deleteProductByIdUseCase;
        this.objectMapper = objectMapper;
    }

    @SqsListener("${sqs.queue.product.delete.listener}")
    public void receiveMessage(String message) throws JsonProcessingException {
        deleteProductByIdUseCase.delete(objectMapper.readValue(message, ProductDeleteDTO.class).id());
    }
}