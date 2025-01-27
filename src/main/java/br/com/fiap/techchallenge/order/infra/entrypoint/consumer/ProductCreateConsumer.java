package br.com.fiap.techchallenge.order.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.order.application.usecase.product.CreateProductUseCase;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.AcceptQueueDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.ProductCreateDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.mapper.ProductMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProductCreateConsumer {

    private final CreateProductUseCase createProductUseCase;
    private final ProductMapper mapper;
    private final SqsTemplate sqsTemplate;

    @Value("${sqs.queue.product.create.accept.producer}")
    private String acceptQueue;

    public ProductCreateConsumer(CreateProductUseCase createProductUseCase, ProductMapper mapper, SqsTemplate sqsTemplate) {
        this.createProductUseCase = createProductUseCase;
        this.mapper = mapper;
        this.sqsTemplate = sqsTemplate;
    }

    @SqsListener("${sqs.queue.product.create.listener}")
    public void receiveMessage(ProductCreateDTO productDTO) {
        createProductUseCase.create(mapper.toProduct(productDTO));

        sqsTemplate.send(acceptQueue, new AcceptQueueDTO("Order", true));
    }
}