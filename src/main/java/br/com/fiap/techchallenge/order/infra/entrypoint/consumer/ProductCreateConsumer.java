package br.com.fiap.techchallenge.order.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.order.application.usecase.product.CreateProductUseCase;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.ProductCreateDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.mapper.ProductMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class ProductCreateConsumer {

    private final CreateProductUseCase createProductUseCase;
    private final ProductMapper mapper;
    private final ObjectMapper objectMapper;

    public ProductCreateConsumer(CreateProductUseCase createProductUseCase, ProductMapper mapper, ObjectMapper objectMapper) {
        this.createProductUseCase = createProductUseCase;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @SqsListener("${sqs.queue.product.create.listener}")
    public void receiveMessage(String message) throws JsonProcessingException {
        createProductUseCase.create(mapper.toProduct(
                objectMapper.readValue(message, ProductCreateDTO.class)));
    }
}